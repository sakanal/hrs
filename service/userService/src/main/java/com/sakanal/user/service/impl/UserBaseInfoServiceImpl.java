package com.sakanal.user.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sakanal.base.utils.PageUtils;
import com.sakanal.base.utils.Query;

import com.sakanal.user.dao.UserBaseInfoDao;
import com.sakanal.user.entity.UserBaseInfoEntity;
import com.sakanal.user.service.UserBaseInfoService;


@Service("userBaseInfoService")
public class UserBaseInfoServiceImpl extends ServiceImpl<UserBaseInfoDao, UserBaseInfoEntity> implements UserBaseInfoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UserBaseInfoEntity> page = this.page(new Query<UserBaseInfoEntity>().getPage(params), new QueryWrapper<>());

        return new PageUtils(page);
    }

}
