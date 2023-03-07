package com.sakanal.user.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sakanal.base.exception.ErrorCodeEnum;
import com.sakanal.base.exception.MyException;
import com.sakanal.base.utils.PageUtils;
import com.sakanal.base.utils.Query;
import com.sakanal.service.dto.LoginOrRegisterSimpleDTO;
import com.sakanal.service.entity.user.UserBaseInfoEntity;
import com.sakanal.service.properties.MyCommonRedisProperties;
import com.sakanal.service.utils.JwtUtils;
import com.sakanal.service.utils.PasswordUtils;
import com.sakanal.service.utils.RedisUtils;
import com.sakanal.service.vo.UserBaseInfoVO;
import com.sakanal.user.dao.UserBaseInfoDao;
import com.sakanal.user.service.UserBaseInfoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@Service("userBaseInfoService")
public class UserBaseInfoServiceImpl extends ServiceImpl<UserBaseInfoDao, UserBaseInfoEntity> implements UserBaseInfoService {
    @Resource
    private MyCommonRedisProperties redisProperties;
    @Resource
    private RedisUtils redisUtils;
    @Value("${common.default-avatar}")
    private String defaultAvatar;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UserBaseInfoEntity> page = this.page(new Query<UserBaseInfoEntity>().getPage(params), new QueryWrapper<>());

        return new PageUtils(page);
    }

    @Override
    public boolean register(LoginOrRegisterSimpleDTO registerSimple) {
        UserBaseInfoEntity userBaseInfo = new UserBaseInfoEntity(registerSimple);
        // 设置随机nickName
        userBaseInfo.setNickName(RandomUtil.randomString(8));
        // 设置默认头像
        userBaseInfo.setHeadPortraitUrl(defaultAvatar);
        return this.save(userBaseInfo);
    }

    @Override
    public boolean checkUserName(String userName) {
        List<UserBaseInfoEntity> list = this.list(new LambdaQueryWrapper<UserBaseInfoEntity>().eq(UserBaseInfoEntity::getUserName, userName).last("limit 1"));
        return list.size() == 0;
    }

    @Override
    public String login(LoginOrRegisterSimpleDTO loginSimple) {
        UserBaseInfoEntity userBaseInfoEntity = this.list(new LambdaQueryWrapper<UserBaseInfoEntity>().eq(UserBaseInfoEntity::getUserName, loginSimple.getUserName()).last("limit 1")).get(0);
        boolean matches = PasswordUtils.matches(loginSimple.getPassword(), userBaseInfoEntity.getPassword());
        if (matches) {
            //登录成功 创建token，保存到redis中
            String token = JwtUtils.getUserToken(userBaseInfoEntity.getId());
            if (!redisUtils.hasKey(redisProperties.getUserInfoPrefix() + userBaseInfoEntity.getId())) {
                UserBaseInfoVO userBaseInfoVO = new UserBaseInfoVO(userBaseInfoEntity);
                // token作为key，userInfo作为value，token中的有效数据为userId
                redisUtils.stringSet(redisProperties.getUserInfoPrefix() + userBaseInfoEntity.getId(), userBaseInfoVO, redisProperties.getExpireTime());
            }
            return token;
        } else {
            return null;
        }
    }

    @Override
    public UserBaseInfoVO getUserInfoByToken(String token) {
        String userId = JwtUtils.getUserIdByToken(token);
        if (userId!=null){
            if (redisUtils.hasKey(redisProperties.getUserInfoPrefix() + userId)){
                // 缓存未过期
                return (UserBaseInfoVO) redisUtils.stringGet(redisProperties.getUserInfoPrefix() + userId);
            }else {
                // 缓存过期，查询数据库并更新缓存
                UserBaseInfoEntity userBaseInfo = this.getById(userId);
                if (userBaseInfo!=null){
                    UserBaseInfoVO userBaseInfoVO = new UserBaseInfoVO(userBaseInfo);
                    redisUtils.stringSet(redisProperties.getUserInfoPrefix() + userId, userBaseInfoVO, redisProperties.getExpireTime());
                    return userBaseInfoVO;
                }else {
                    // token中的数据无效
                    throw new MyException(ErrorCodeEnum.TOKEN_INVALID_EXCEPTION.getMsg(),ErrorCodeEnum.TOKEN_INVALID_EXCEPTION.getCode());
                }
            }
        }else {
            // token无效
            throw new MyException(ErrorCodeEnum.TOKEN_EXPIRE_EXCEPTION.getMsg(),ErrorCodeEnum.TOKEN_EXPIRE_EXCEPTION.getCode());
        }
    }

}
