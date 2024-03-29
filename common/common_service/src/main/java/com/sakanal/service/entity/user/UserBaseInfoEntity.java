package com.sakanal.service.entity.user;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sakanal.service.dto.ChangePasswordDTO;
import com.sakanal.service.dto.RegisterDTO;
import com.sakanal.service.utils.PasswordUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户基础数据表
 *
 * @author sakanal
 * @email 1104820805@qq.com
 * @date 2023-02-22 13:24:10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("user_base_info")
public class UserBaseInfoEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 头像url
     */
    private String headPortraitUrl;
    /**
     * 电话
     */
    private Long phone;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 密码
     */
    private String password;
    /**
     * 是否删除 0-未删除 1-已删除
     */
    @TableLogic
    private Integer isDeleted;
    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT)
    private Date createdTime;
    /**
     * 修改时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifyTime;

    public UserBaseInfoEntity(RegisterDTO registerSimple) {
        // 对密码加密
        registerSimple.setPassword(PasswordUtils.encode(registerSimple.getPassword()));
        BeanUtils.copyProperties(registerSimple,this);
        this.phone= Long.valueOf(registerSimple.getPhone());
    }

    public UserBaseInfoEntity(Long userId, ChangePasswordDTO changePasswordDTO) {
        this.id=userId;
        this.password=PasswordUtils.encode(changePasswordDTO.getNewPassword());
    }
}
