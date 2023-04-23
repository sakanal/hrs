package com.sakanal.house.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sakanal.base.constant.PublishStateConstant;
import com.sakanal.base.utils.PageUtils;
import com.sakanal.base.utils.Query;
import com.sakanal.house.dao.HousePromotionDao;
import com.sakanal.house.service.HousePromotionService;
import com.sakanal.house.service.HouseStateService;
import com.sakanal.service.dto.PromotionNumberDTO;
import com.sakanal.service.entity.house.HousePromotionEntity;
import com.sakanal.service.entity.house.HouseStateEntity;
import com.sakanal.service.properties.MyCommonRedisProperties;
import com.sakanal.service.utils.RedisUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@Service("housePromotionService")
public class HousePromotionServiceImpl extends ServiceImpl<HousePromotionDao, HousePromotionEntity> implements HousePromotionService {
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private MyCommonRedisProperties redisProperties;
    @Resource
    private HouseStateService stateService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<HousePromotionEntity> page = this.page(new Query<HousePromotionEntity>().getPage(params), new QueryWrapper<>());

        return new PageUtils(page);
    }

    @Override
    public Integer getVisitorNumber(Long baseInfoId) {
        // TODO 数据一致性问题，有可能丢失一个小时内的所有浏览人数数据
        Integer number = (Integer) redisUtils.stringGet(redisProperties.getVisitorPrefix() + baseInfoId);
        if (number == null) {
            // redis中没有数据，数据过期或本身就没有数据
            HousePromotionEntity promotionEntity = this.getById(baseInfoId);
            if (promotionEntity != null) {
                // 数据库中有数据，即redis中的数据过期
                Integer visitorNumber = promotionEntity.getVisitorNumber();
                redisUtils.stringSet(redisProperties.getVisitorPrefix() + baseInfoId, visitorNumber + 1, redisProperties.getVisitorExpireTime());
                number = visitorNumber + 1;
            } else {
                // 本身就没有数据，需要设置数据，主要是需要设置过期时间
                redisUtils.stringSet(redisProperties.getVisitorPrefix() + baseInfoId, 1, redisProperties.getVisitorExpireTime());
                number = 1;
            }
        }
        return number;
    }

    @Override
    public boolean incrVisitorNumber(Long baseInfoId) {
        long result = redisUtils.stringIncr(redisProperties.getVisitorPrefix() + baseInfoId, 1L);
        return result == 1;
    }

    @Override
    public void decrPromotionNumber(List<Long> baseInfoIdList) {
        if (baseInfoIdList!=null && baseInfoIdList.size()>0){
            baseMapper.reducePromotionNumber(baseInfoIdList);
        }
    }

    @Override
    public boolean addPromotionNumber(PromotionNumberDTO promotionNumberDTO) {
        if (promotionNumberDTO != null) {
            if (promotionNumberDTO.getBaseInfoId() > 0) {
                // 为单个房源
                return addPromotionNumberToBD(promotionNumberDTO.getBaseInfoId(), promotionNumberDTO.getPromotionNumber());
            } else {
                // 为当前所有发布的房源
                Long number = -promotionNumberDTO.getBaseInfoId();
                long promotionNumber = promotionNumberDTO.getPromotionNumber() / number;
                stateService.list(new LambdaQueryWrapper<HouseStateEntity>()
                                .eq(HouseStateEntity::getPublisherId, promotionNumberDTO.getUserId())
                                .eq(HouseStateEntity::getHousePublishState, PublishStateConstant.PUBLISH_STATE))
                        .forEach(houseStateEntity -> addPromotionNumberToBD(houseStateEntity.getBaseInfoId(), promotionNumber));
                return true;
            }
        }
        return false;
    }

    private boolean addPromotionNumberToBD(Long baseInfoId, Long promotionNumber) {
        if (baseMapper.addPromotionNumber(baseInfoId, promotionNumber)) {
            // 存在数据，更新成功
            return true;
        } else {
            // 不存在数据，更新失败，进行新增
            return this.save(new HousePromotionEntity(baseInfoId, promotionNumber));
        }
    }

}
