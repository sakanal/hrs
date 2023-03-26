package com.sakanal.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sakanal.service.utils.JwtUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sakanal.base.utils.PageUtils;
import com.sakanal.base.utils.Query;

import com.sakanal.user.dao.UserBrowseDao;
import com.sakanal.service.entity.user.UserBrowseEntity;
import com.sakanal.user.service.UserBrowseService;
import org.springframework.util.StringUtils;


@Service("userBrowseService")
public class UserBrowseServiceImpl extends ServiceImpl<UserBrowseDao, UserBrowseEntity> implements UserBrowseService {

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
            if (userBrowse!=null){
                // 数据库中有数据
                userBrowseEntity.setId(userBrowse.getId());
                this.updateById(userBrowseEntity);
            }else {
                // 数据库中没有数据
                this.save(userBrowseEntity);
            }
        }
    }

}
