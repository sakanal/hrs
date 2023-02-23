package com.sakanal.user.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sakanal.base.utils.PageUtils;
import com.sakanal.base.utils.Query;

import com.sakanal.user.dao.UserCollectionDao;
import com.sakanal.service.entity.user.UserCollectionEntity;
import com.sakanal.user.service.UserCollectionService;


@Service("userCollectionService")
public class UserCollectionServiceImpl extends ServiceImpl<UserCollectionDao, UserCollectionEntity> implements UserCollectionService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UserCollectionEntity> page = this.page(new Query<UserCollectionEntity>().getPage(params), new QueryWrapper<>());

        return new PageUtils(page);
    }

}
