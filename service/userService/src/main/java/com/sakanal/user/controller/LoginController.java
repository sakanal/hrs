package com.sakanal.user.controller;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.TypeReference;
import com.sakanal.base.exception.ErrorCodeEnum;
import com.sakanal.base.utils.R;
import com.sakanal.service.dto.LoginPhoneDTO;
import com.sakanal.service.dto.LoginSimpleDTO;
import com.sakanal.service.properties.MyCommonRedisProperties;
import com.sakanal.service.utils.RedisUtils;
import com.sakanal.service.vo.UserBaseInfoVO;
import com.sakanal.user.feign.SMSFeignClient;
import com.sakanal.user.service.UserBaseInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/user/login")
public class LoginController {
    @Resource
    private UserBaseInfoService userBaseInfoService;
    @Resource
    private SMSFeignClient smsFeignClient;
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private MyCommonRedisProperties redisProperties;


    @PostMapping("/simple")
    public R loginSimple(@RequestBody @Valid LoginSimpleDTO loginSimple) {
        String token = userBaseInfoService.login(loginSimple);
        if (StringUtils.hasText(token)) {
            return R.ok().put("token", token);
        } else {
            return R.error(ErrorCodeEnum.LOGIN_FAIL_EXCEPTION.getCode(), ErrorCodeEnum.LOGIN_FAIL_EXCEPTION.getMsg());
        }
    }
    @PostMapping("/phone")
    public R loginPhone(@RequestBody @Valid LoginPhoneDTO loginPhone){
        String token = userBaseInfoService.login(loginPhone);
        if (StringUtils.hasText(token)) {
            return R.ok().put("token", token);
        } else {
            return R.error(ErrorCodeEnum.LOGIN_FAIL_EXCEPTION.getCode(), ErrorCodeEnum.LOGIN_FAIL_EXCEPTION.getMsg());
        }
    }

    @GetMapping("/userInfoByToken")
    public R userInfoByToken(@RequestHeader(value = "token") String token) {
        UserBaseInfoVO userBaseInfoVO = userBaseInfoService.getUserInfoByToken(token);
        if (userBaseInfoVO != null) {
            return R.ok().put("userInfo", userBaseInfoVO);
        } else {
            return R.error();
        }
    }

    @RequestMapping("/sendCode/{phone}")
    public R sendCode(@PathVariable String phone) {
        if (!redisUtils.hasKey(redisProperties.getLoginCodePrefix() + phone)) {
            String code = RandomUtil.randomNumbers(6);
            Integer result = smsFeignClient.send(code, phone).getData("code", new TypeReference<Integer>() {
            });
            if (result == 0) {
                redisUtils.stringSet(redisProperties.getLoginCodePrefix() + phone, code, redisProperties.getCodeExpireTime());
            } else {
                return R.error(ErrorCodeEnum.SMS_FAIL_EXCEPTION.getCode(), ErrorCodeEnum.SMS_FAIL_EXCEPTION.getMsg());
            }
        }
        return R.ok();
    }
}
