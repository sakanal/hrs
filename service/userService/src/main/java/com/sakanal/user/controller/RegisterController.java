package com.sakanal.user.controller;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.TypeReference;
import com.sakanal.base.exception.ErrorCodeEnum;
import com.sakanal.base.utils.R;
import com.sakanal.service.dto.RegisterDTO;
import com.sakanal.service.properties.MyCommonRedisProperties;
import com.sakanal.service.utils.RedisUtils;
import com.sakanal.user.feign.SMSFeignClient;
import com.sakanal.user.service.UserBaseInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/user/register")
public class RegisterController {
    @Resource
    private UserBaseInfoService userBaseInfoService;
    @Resource
    private SMSFeignClient smsFeignClient;
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private MyCommonRedisProperties redisProperties;

    /**
     * 注册账号
     */
    @RequestMapping("/simple")
    //@RequiresPermissions("user:userbaseinfo:save")
    public R registerSimple(@RequestBody @Valid RegisterDTO registerSimple) {
        if (userBaseInfoService.register(registerSimple)) {
            return R.ok("注册成功");
        } else {
            return R.error(ErrorCodeEnum.REGISTER_FAIL_EXCEPTION.getCode(), ErrorCodeEnum.REGISTER_FAIL_EXCEPTION.getMsg());
        }
    }

    /**
     * 检测用户名是否重复
     */
    @GetMapping("/checkUserName/{userName}")
    public R checkUserName(@PathVariable String userName) {
        return R.ok().put("result", userBaseInfoService.checkUserName(userName));
    }

    @RequestMapping("/sendCode/{phone}")
    public R sendCode(@PathVariable String phone) {
        if (userBaseInfoService.userCount(Long.valueOf(phone), null)==0) {
            if (!redisUtils.hasKey(redisProperties.getRegisterCodePrefix() + phone)) {
                String code = RandomUtil.randomNumbers(6);
                Integer result = smsFeignClient.send(code, phone).getData("code", new TypeReference<Integer>() {
                });
                if (result == 0) {
                    redisUtils.stringSet(redisProperties.getRegisterCodePrefix() + phone, code, redisProperties.getCodeExpireTime());
                } else {
                    return R.error(ErrorCodeEnum.SMS_FAIL_EXCEPTION.getCode(), ErrorCodeEnum.SMS_FAIL_EXCEPTION.getMsg());
                }
            }
        } else {
            return R.error(ErrorCodeEnum.REPEAT_PHONE_EXCEPTION.getCode(), ErrorCodeEnum.REPEAT_PHONE_EXCEPTION.getMsg());
        }
        return R.ok();
    }
}
