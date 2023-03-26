package com.sakanal.user.service.impl;

import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sakanal.base.constant.PageConstant;
import com.sakanal.base.utils.PageUtils;
import com.sakanal.base.utils.Query;
import com.sakanal.base.utils.R;
import com.sakanal.service.dto.PublishInfoListDTO;
import com.sakanal.service.entity.user.UserBrowseEntity;
import com.sakanal.service.utils.JwtUtils;
import com.sakanal.service.vo.PublishBaseInfoVO;
import com.sakanal.user.dao.UserBrowseDao;
import com.sakanal.user.feign.HouseFeignClient;
import com.sakanal.user.service.UserBrowseService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;


@Service("userBrowseService")
public class UserBrowseServiceImpl extends ServiceImpl<UserBrowseDao, UserBrowseEntity> implements UserBrowseService {

    @Resource
    private HouseFeignClient houseFeignClient;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UserBrowseEntity> page = this.page(new Query<UserBrowseEntity>().getPage(params), new QueryWrapper<>());

        return new PageUtils(page);
    }

    @Override
    public void browse(String token, Long houseBaseId) {
        String userId = JwtUtils.getUserIdByToken(token);
        if (StringUtils.hasText(userId) && houseBaseId!=null){
            // 先判断数据库中是否有对应的数据
            UserBrowseEntity userBrowse = this.getOne(new LambdaQueryWrapper<UserBrowseEntity>()
                    .eq(UserBrowseEntity::getUserId, userId)
                    .eq(UserBrowseEntity::getBaseInfoId, houseBaseId)
            );
            UserBrowseEntity userBrowseEntity = new UserBrowseEntity();
            userBrowseEntity.setUserId(Long.valueOf(userId));
            userBrowseEntity.setBaseInfoId(houseBaseId);
            userBrowseEntity.setLastBrowseTime(new Date());
            if (userBrowse != null) {
                // 数据库中有数据
                userBrowseEntity.setId(userBrowse.getId());
                this.updateById(userBrowseEntity);
            } else {
                // 数据库中没有数据
                this.save(userBrowseEntity);
            }
        }
    }

    @Override
    public PageUtils getMyBrowse(String token,Integer current) {
        String userId = JwtUtils.getUserIdByToken(token);
        if (StringUtils.hasText(userId)) {
            List<UserBrowseEntity> list = this.list(new LambdaQueryWrapper<UserBrowseEntity>()
                    .select(UserBrowseEntity::getBaseInfoId,UserBrowseEntity::getLastBrowseTime)
                    .eq(UserBrowseEntity::getUserId, userId)
                    .orderByDesc(UserBrowseEntity::getLastBrowseTime)
                    .last("limit "+((current-1)*PageConstant.LIMIT)+","+PageConstant.LIMIT)
            );
            long total = this.count(new LambdaQueryWrapper<UserBrowseEntity>()
                    .eq(UserBrowseEntity::getUserId, userId)
            );
            List<Long> baseInfoIdList = list.stream().map(UserBrowseEntity::getBaseInfoId).collect(Collectors.toList());
            if (baseInfoIdList.size()>0){
                // 构建搜索条件
                PublishInfoListDTO publishInfoListDTO = new PublishInfoListDTO();
                publishInfoListDTO.setBaseInfoIdList(baseInfoIdList);
                publishInfoListDTO.setCurrent(current);
                R r = houseFeignClient.getBrowsePublishInfoList(publishInfoListDTO);
                List<PublishBaseInfoVO> publishBaseInfoVOList = r.getData("data", new TypeReference<List<PublishBaseInfoVO>>() {
                });
                List<PublishBaseInfoVO> result = new ArrayList<>();
                // 重构结果，按照时间倒序排列
                for (UserBrowseEntity userBrowseEntity : list) {
                    Long baseInfoId = userBrowseEntity.getBaseInfoId();
                    Date lastBrowseTime = userBrowseEntity.getLastBrowseTime();
                    publishBaseInfoVOList.forEach(publishBaseInfoVO -> {
                        if (Objects.equals(baseInfoId, publishBaseInfoVO.getBaseInfoId())) {
                            publishBaseInfoVO.setLastBrowseTime(lastBrowseTime);
                            result.add(publishBaseInfoVO);
                        }
                    });
                }
                return new PageUtils(result,Math.toIntExact(total), PageConstant.LIMIT,current);
            }
        }
        return null;
    }

}
