package com.sakanal.user.controller;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sakanal.base.exception.ErrorCodeEnum;
import com.sakanal.base.utils.PageUtils;
import com.sakanal.base.utils.R;
import com.sakanal.service.dto.ChangePasswordDTO;
import com.sakanal.service.entity.user.UserBaseInfoEntity;
import com.sakanal.service.properties.MyCommonRedisProperties;
import com.sakanal.service.utils.RedisUtils;
import com.sakanal.service.vo.UserBaseInfoVO;
import com.sakanal.user.feign.SMSFeignClient;
import com.sakanal.user.service.UserBaseInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.Map;


/**
 * 用户基础数据表
 *
 * @author sakanal
 * @email 1104820805@qq.com
 * @date 2023-02-22 13:24:10
 */
@Slf4j
@RestController
@RequestMapping("user/userbaseinfo")
public class UserBaseInfoController {
    @Resource
    private UserBaseInfoService userBaseInfoService;
    @Resource
    private SMSFeignClient smsFeignClient;
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private MyCommonRedisProperties redisProperties;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("user:userbaseinfo:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = userBaseInfoService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("user:userbaseinfo:info")
    public R info(@PathVariable("id") Long id){
		UserBaseInfoEntity userBaseInfo = userBaseInfoService.getById(id);

        return R.ok().put("userBaseInfo", userBaseInfo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("user:userbaseinfo:save")
    public R save(@RequestBody UserBaseInfoEntity userBaseInfo){
		userBaseInfoService.save(userBaseInfo);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("user:userbaseinfo:update")
    public R update(@RequestBody UserBaseInfoEntity userBaseInfo){
		userBaseInfoService.updateById(userBaseInfo);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("user:userbaseinfo:delete")
    public R delete(@RequestBody Long[] ids){
		userBaseInfoService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    @PutMapping("/updateUserInfo")
    public R updateUserInfo(@RequestBody Map<String,Object> params){
        if (userBaseInfoService.updateUserInfo(params)){
            return R.ok();
        }else {
            return R.error();
        }
    }
    @PutMapping("/changePassword")
    public R changePassword(@Valid @RequestBody ChangePasswordDTO changePasswordDTO){
        if (userBaseInfoService.changePassword(changePasswordDTO)){
            return R.ok();
        }else {
            return R.error();
        }
    }

    @GetMapping("/getPhone/{userId}")
    public R userInfoByToken(@PathVariable Long userId) {
        UserBaseInfoEntity userBaseInfo = userBaseInfoService.getById(userId);
        if (userBaseInfo != null) {
            return R.ok().put("phone", userBaseInfo.getPhone());
        } else {
            return R.error();
        }
    }
    @RequestMapping("/sendCode/{userId}/{phone}")
    public R sendCode(@PathVariable Long userId, @PathVariable String phone) {
        if (userBaseInfoService.count(new LambdaQueryWrapper<UserBaseInfoEntity>().eq(UserBaseInfoEntity::getId,userId))==1){
            if (!redisUtils.hasKey(redisProperties.getBindPhoneCodePrefix() + userId)) {
                String code = RandomUtil.randomNumbers(6);
                Integer result = smsFeignClient.send(code, phone).getData("code", new TypeReference<Integer>() {
                });
                if (result == 0) {
                    redisUtils.stringSet(redisProperties.getBindPhoneCodePrefix() + userId, code, redisProperties.getCodeExpireTime());
                } else {
                    return R.error(ErrorCodeEnum.SMS_FAIL_EXCEPTION.getCode(), ErrorCodeEnum.SMS_FAIL_EXCEPTION.getMsg());
                }
            }
        }else {
            return R.error(ErrorCodeEnum.PHONE_INVALID_EXCEPTION.getCode(), ErrorCodeEnum.PHONE_INVALID_EXCEPTION.getMsg());
        }
        return R.ok();
    }
}
