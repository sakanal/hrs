package com.sakanal.house.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sakanal.base.utils.PageUtils;
import com.sakanal.base.utils.Query;
import com.sakanal.house.dao.HousePromotionDao;
import com.sakanal.house.service.HousePromotionService;
import com.sakanal.service.entity.house.HousePromotionEntity;
import com.sakanal.service.properties.MyCommonRedisProperties;
import com.sakanal.service.utils.RedisUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;


@Service("housePromotionService")
public class HousePromotionServiceImpl extends ServiceImpl<HousePromotionDao, HousePromotionEntity> implements HousePromotionService {
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private MyCommonRedisProperties redisProperties;

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
        return result==1;
    }

}
