package com.sakanal.user.service.impl;

import cn.hutool.core.util.DesensitizedUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sakanal.base.exception.ErrorCodeEnum;
import com.sakanal.base.exception.MyException;
import com.sakanal.base.utils.PageUtils;
import com.sakanal.base.utils.Query;
import com.sakanal.service.dto.ChangePasswordDTO;
import com.sakanal.service.dto.LoginPhoneDTO;
import com.sakanal.service.dto.LoginSimpleDTO;
import com.sakanal.service.dto.RegisterDTO;
import com.sakanal.service.entity.user.UserBaseInfoEntity;
import com.sakanal.service.properties.MyCommonRedisProperties;
import com.sakanal.service.utils.JwtUtils;
import com.sakanal.service.utils.PasswordUtils;
import com.sakanal.service.utils.RedisUtils;
import com.sakanal.service.vo.UserBaseInfoVO;
import com.sakanal.user.dao.UserBaseInfoDao;
import com.sakanal.user.feign.HouseFeignClient;
import com.sakanal.user.service.UserBaseInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@Slf4j
@Service("userBaseInfoService")
public class UserBaseInfoServiceImpl extends ServiceImpl<UserBaseInfoDao, UserBaseInfoEntity> implements UserBaseInfoService {
    @Resource
    private MyCommonRedisProperties redisProperties;
    @Resource
    private RedisUtils redisUtils;
    @Value("${common.default-avatar}")
    private String defaultAvatar;
    @Resource
    private HouseFeignClient houseFeignClient;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UserBaseInfoEntity> page = this.page(new Query<UserBaseInfoEntity>().getPage(params), new QueryWrapper<>());

        return new PageUtils(page);
    }

    @Override
    public boolean register(RegisterDTO registerSimple) {
        Long phone = Long.valueOf(registerSimple.getPhone());
        String redisCode = (String) redisUtils.stringGet(redisProperties.getRegisterCodePrefix() + phone);
        if (Objects.equals(registerSimple.getCode(), redisCode)) {
            if (userCount(phone, registerSimple.getUserName())==0) {
                UserBaseInfoEntity userBaseInfo = new UserBaseInfoEntity(registerSimple);
                // 设置随机nickName
                userBaseInfo.setNickName(RandomUtil.randomString(8));
                // 设置默认头像
                userBaseInfo.setHeadPortraitUrl(defaultAvatar);
                return this.save(userBaseInfo);
            }
        } else {
            throw new MyException(ErrorCodeEnum.SMS_INVALID_EXCEPTION.getMsg(), ErrorCodeEnum.SMS_INVALID_EXCEPTION.getCode());
        }
        return false;
    }

    @Override
    public boolean checkUserName(String userName) {
        List<UserBaseInfoEntity> list = this.list(new LambdaQueryWrapper<UserBaseInfoEntity>().eq(UserBaseInfoEntity::getUserName, userName).last("limit 1"));
        return list.size() == 0;
    }

    @Override
    public long userCount(Long phone, String userName) {
        LambdaQueryWrapper<UserBaseInfoEntity> lambdaQueryWrapper = new LambdaQueryWrapper<UserBaseInfoEntity>();
        lambdaQueryWrapper.eq(UserBaseInfoEntity::getPhone, phone);
        if (StringUtils.hasText(userName)) {
            lambdaQueryWrapper.or().eq(UserBaseInfoEntity::getUserName, userName);
        }
        return this.count(lambdaQueryWrapper);
    }


    private String createToken(UserBaseInfoEntity userBaseInfoEntity) {
        String token = JwtUtils.getUserToken(userBaseInfoEntity.getId());
        if (!redisUtils.hasKey(redisProperties.getUserInfoPrefix() + userBaseInfoEntity.getId())) {
            UserBaseInfoVO userBaseInfoVO = new UserBaseInfoVO(userBaseInfoEntity);
            // token作为key，userInfo作为value，token中的有效数据为userId
            redisUtils.stringSet(redisProperties.getUserInfoPrefix() + userBaseInfoEntity.getId(), userBaseInfoVO, redisProperties.getExpireTime());
        }
        return token;
    }

    @Override
    public String login(LoginSimpleDTO loginSimple) {
        List<UserBaseInfoEntity> list = this.list(new LambdaQueryWrapper<UserBaseInfoEntity>().eq(UserBaseInfoEntity::getUserName, loginSimple.getUserName()));
        if (list.size()==1){
            UserBaseInfoEntity userBaseInfoEntity = list.get(0);
            boolean matches = PasswordUtils.matches(loginSimple.getPassword(), userBaseInfoEntity.getPassword());
            if (matches) {
                //登录成功 创建token，保存到redis中
                return createToken(userBaseInfoEntity);
            }else {
                throw new MyException(ErrorCodeEnum.PASSWORD_INVALID_EXCEPTION.getMsg(),ErrorCodeEnum.PASSWORD_INVALID_EXCEPTION.getCode());
            }
        }else {
            throw new MyException(ErrorCodeEnum.USER_INVALID_EXCEPTION.getMsg(),ErrorCodeEnum.USER_INVALID_EXCEPTION.getCode());
        }
    }

    @Override
    public String login(LoginPhoneDTO loginPhone) {
        List<UserBaseInfoEntity> list = this.list(new LambdaQueryWrapper<UserBaseInfoEntity>().eq(UserBaseInfoEntity::getPhone, loginPhone.getPhone()));
        if (list.size()==1){
            String redisCode = (String) redisUtils.stringGet(redisProperties.getLoginCodePrefix() + loginPhone.getPhone());
            if (Objects.equals(redisCode,loginPhone.getCode())){
                UserBaseInfoEntity userBaseInfoEntity = list.get(0);
                // 删除缓存中的验证码--保证验证码一次有效
                redisUtils.del(redisProperties.getLoginCodePrefix() + loginPhone.getPhone());
                //登录成功 创建token，保存到redis中
                return createToken(userBaseInfoEntity);
            } else {
                throw new MyException(ErrorCodeEnum.SMS_INVALID_EXCEPTION.getMsg(), ErrorCodeEnum.SMS_INVALID_EXCEPTION.getCode());
            }
        }
        return null;
    }

    @Override
    public UserBaseInfoVO getUserInfoByToken(String token) {
        String userId = JwtUtils.getUserIdByToken(token);
        if (userId!=null){
            if (redisUtils.hasKey(redisProperties.getUserInfoPrefix() + userId)){
                // 缓存未过期
                return (UserBaseInfoVO) redisUtils.stringGet(redisProperties.getUserInfoPrefix() + userId);
            }else {
                // 缓存过期，登录失效
                throw new MyException(ErrorCodeEnum.TOKEN_EXPIRE_EXCEPTION.getMsg(), ErrorCodeEnum.TOKEN_EXPIRE_EXCEPTION.getCode());
            }
        } else {
            // token无效
            throw new MyException(ErrorCodeEnum.TOKEN_EXPIRE_EXCEPTION.getMsg(), ErrorCodeEnum.TOKEN_EXPIRE_EXCEPTION.getCode());
        }
    }

    @Override
    public boolean updateAvatar(Map<String, Object> params) {
        String uuid = (String) params.get("uuid");
        if (!StringUtils.hasText(uuid)) {
            return false;
        }
        String avatarUrl = (String) redisUtils.hashGet(redisProperties.getAvatarTempPrefix(), uuid);
        if (!StringUtils.hasText(avatarUrl)) {
            return false;
        }
        long userId = Long.parseLong((String) params.get("userId"));
        if (userId < 0) {
            return false;
        }
        UserBaseInfoEntity userBaseInfoEntity = new UserBaseInfoEntity();
        userBaseInfoEntity.setId(userId);
        userBaseInfoEntity.setHeadPortraitUrl(avatarUrl);
        if (this.updateById(userBaseInfoEntity)) {
            redisUtils.hashDel(redisProperties.getAvatarTempPrefix(), uuid);
//            redisUtils.del(redisProperties.getUserInfoPrefix() + userId);
            UserBaseInfoVO userBaseInfoVO = new UserBaseInfoVO(this.getById(userId));
            long expire = redisUtils.getExpire(redisProperties.getUserInfoPrefix() + userId);
            redisUtils.stringSet(redisProperties.getUserInfoPrefix()+userId,userBaseInfoVO,expire);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean updateUserInfo(Map<String, Object> params) {
        String userIdString = (String) params.get("userId");
        if (StringUtils.hasText(userIdString)){
            long userId = Long.parseLong(userIdString);
            boolean changeFlag=false,changeNickNameFlag=false;
            UserBaseInfoEntity userBaseInfoEntity = new UserBaseInfoEntity();
            userBaseInfoEntity.setId(userId);
            String userName = (String) params.get("userName");
            if (StringUtils.hasText(userName)){
                String userNameRegex="^[a-zA-Z][a-zA-Z0-9_]{4,15}$";
                if (userName.matches(userNameRegex)){
                    userBaseInfoEntity.setUserName(userName);
                    changeFlag=true;
                }else {
                    throw new MyException("无效数据");
                }
            }
            String nickName = (String) params.get("nickName");
            if (StringUtils.hasText(nickName)){
                String nickNameRegex="^[\\u4E00-\\u9FA5A-Za-z0-9_]+$";
                if ( nickName.matches(nickNameRegex)){
                    userBaseInfoEntity.setNickName(nickName);
                    changeFlag=true;
                    changeNickNameFlag=true;
                }else {
                    throw new MyException("无效数据");
                }
            }
            String phoneString = (String) params.get("phone");
            if (StringUtils.hasText(phoneString)){
                String code = (String) params.get("code");
                if (StringUtils.hasText(code)){
                    String codeRedis = (String) redisUtils.stringGet(redisProperties.getBindPhoneCodePrefix() + userId);
                    if (Objects.equals(codeRedis,code)){
                        String phoneRegex="^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$";
                        if (phoneString.matches(phoneRegex)){
                            userBaseInfoEntity.setPhone(Long.valueOf(phoneString));
                            changeFlag=true;
                            redisUtils.del(redisProperties.getBindPhoneCodePrefix()+userId);
                        }else {
                            throw new MyException("无效数据");
                        }
                    }
                }
            }
            String email = (String) params.get("email");
            if (StringUtils.hasText(email)){
                String emailRegex ="^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
                if (email.matches(emailRegex)){
                    userBaseInfoEntity.setEmail(email);
                    changeFlag=true;
                }else {
                    throw new MyException("无效数据");
                }
            }
            if (changeFlag){
                if (this.updateById(userBaseInfoEntity)){
                    // 更新成功，删除缓存
                    if (changeNickNameFlag){
                        // 修改房源发布人昵称
                        houseFeignClient.updatePublisherNameByUserId(params);
                    }
//                    redisUtils.del(redisProperties.getUserInfoPrefix()+userId);
                    UserBaseInfoVO userBaseInfoVO = new UserBaseInfoVO(this.getById(userId));
                    long expire = redisUtils.getExpire(redisProperties.getUserInfoPrefix() + userId);
                    redisUtils.stringSet(redisProperties.getUserInfoPrefix()+userId,userBaseInfoVO,expire);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean changePassword(ChangePasswordDTO changePasswordDTO) {
        Long userId = changePasswordDTO.getUserId();
        String oldPassword = changePasswordDTO.getOldPassword();
        String newPassword = changePasswordDTO.getNewPassword();
        String checkPassword = changePasswordDTO.getCheckPassword();
        if (Objects.equals(newPassword,checkPassword)){
            UserBaseInfoEntity userBaseInfoEntity = this.getById(userId);
            String password = userBaseInfoEntity.getPassword();
            if (PasswordUtils.matches(oldPassword,password)){
                // 旧密码正确
                if (!PasswordUtils.matches(newPassword,password)){
                    UserBaseInfoEntity userBaseInfo = new UserBaseInfoEntity(userId,changePasswordDTO);
                    if (this.updateById(userBaseInfo)){
                        // 删除redis中的数据
                        redisUtils.del(redisProperties.getUserInfoPrefix()+userId);
                        return true;
                    }else {
                        throw new MyException("修改密码失败");
                    }
                }else {
                    throw new MyException("新密码与旧密码不能相同");
                }
            }else {
                throw new MyException("旧密码不正确");
            }
        }else {
            throw new MyException("密码不一致");
        }
    }

}
