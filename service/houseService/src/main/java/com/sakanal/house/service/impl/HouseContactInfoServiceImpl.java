package com.sakanal.house.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sakanal.base.utils.PageUtils;
import com.sakanal.base.utils.Query;

import com.sakanal.house.dao.HouseContactInfoDao;
import com.sakanal.service.entity.house.HouseContactInfoEntity;
import com.sakanal.house.service.HouseContactInfoService;
import org.springframework.util.StringUtils;


@Service("houseContactInfoService")
public class HouseContactInfoServiceImpl extends ServiceImpl<HouseContactInfoDao, HouseContactInfoEntity> implements HouseContactInfoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<HouseContactInfoEntity> page = this.page(new Query<HouseContactInfoEntity>().getPage(params), new QueryWrapper<HouseContactInfoEntity>());

        return new PageUtils(page);
    }

    @Override
    public boolean updatePublisherNameByUserId(Map<String, Object> params) {
        String userIdString = (String) params.get("userId");
        if (StringUtils.hasText(userIdString)){
            long userId = Long.parseLong(userIdString);
            String nickName = (String) params.get("nickName");
            if (StringUtils.hasText(nickName)){
                return baseMapper.updatePublisherNameByUserId(userId,nickName);
            }
        }
        return false;
    }

    @Override
    public boolean updatePublisherPhoneByUserId(Map<String, Object> params) {
        String userIdString = (String) params.get("userId");
        if (StringUtils.hasText(userIdString)){
            long userId = Long.parseLong(userIdString);
            String phone = (String) params.get("phone");
            if(StringUtils.hasText(phone)){
                return baseMapper.updatePublisherPhoneByUserId(userId,phone);
            }
        }
        return false;
    }

}
