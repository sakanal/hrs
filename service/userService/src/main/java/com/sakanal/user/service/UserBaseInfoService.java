package com.sakanal.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sakanal.base.utils.PageUtils;
import com.sakanal.service.dto.ChangePasswordDTO;
import com.sakanal.service.dto.LoginPhoneDTO;
import com.sakanal.service.dto.LoginSimpleDTO;
import com.sakanal.service.dto.RegisterDTO;
import com.sakanal.service.entity.user.UserBaseInfoEntity;
import com.sakanal.service.vo.UserBaseInfoVO;

import java.util.Map;

/**
 * 用户基础数据表
 *
 * @author sakanal
 * @email 1104820805@qq.com
 * @date 2023-02-22 13:24:10
 */
public interface UserBaseInfoService extends IService<UserBaseInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    boolean register(RegisterDTO registerSimple);

    boolean checkUserName(String userName);

    boolean userOnly(Long phone,String userName);

    String login(LoginSimpleDTO loginSimple);

    String login(LoginPhoneDTO loginPhone);

    UserBaseInfoVO getUserInfoByToken(String token);

    /**
     * 更新头像，params中有redis（hash）中的item（uuid）和用户id
     */
    boolean updateAvatar(Map<String, Object> params);

    boolean updateUserInfo(Map<String, Object> params);

    boolean changePassword(ChangePasswordDTO changePasswordDTO);

}

