package com.sakanal.user.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sakanal.base.utils.PageUtils;
import com.sakanal.base.utils.Query;

import com.sakanal.user.dao.UserBrowseDao;
import com.sakanal.service.entity.user.UserBrowseEntity;
import com.sakanal.user.service.UserBrowseService;


@Service("userBrowseService")
public class UserBrowseServiceImpl extends ServiceImpl<UserBrowseDao, UserBrowseEntity> implements UserBrowseService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UserBrowseEntity> page = this.page(new Query<UserBrowseEntity>().getPage(params), new QueryWrapper<>());

        return new PageUtils(page);
    }

}
