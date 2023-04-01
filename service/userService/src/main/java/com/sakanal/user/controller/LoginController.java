package com.sakanal.user.controller;

import com.sakanal.base.exception.ErrorCodeEnum;
import com.sakanal.base.utils.R;
import com.sakanal.service.dto.LoginSimpleDTO;
import com.sakanal.service.vo.UserBaseInfoVO;
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

    @PostMapping("/simple")
    public R loginSimple(@RequestBody @Valid LoginSimpleDTO loginSimple) {
        String token = userBaseInfoService.login(loginSimple);
        if (StringUtils.hasText(token)) {
            return R.ok().put("token", token);
        } else {
            return R.error(ErrorCodeEnum.LOGIN_FAIL_EXCEPTION.getCode(), ErrorCodeEnum.LOGIN_FAIL_EXCEPTION.getMsg());
        }
    }

    @GetMapping("/userInfoByToken")
    public R userInfoByToken(@RequestHeader(value = "token") String token) {
        UserBaseInfoVO userBaseInfoVO = userBaseInfoService.getUserInfoByToken(token);
        if (userBaseInfoVO!=null){
            return R.ok().put("userInfo",userBaseInfoVO);
        }else {
            return R.error();
        }
    }
}
