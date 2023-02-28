package com.sakanal.user.controller;

import com.sakanal.base.exception.ErrorCodeEnum;
import com.sakanal.base.utils.R;
import com.sakanal.service.dto.LoginOrRegisterSimpleDTO;
import com.sakanal.user.service.UserBaseInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@Slf4j
@RestController
public class RegisterController {
    @Resource
    private UserBaseInfoService userBaseInfoService;

    /**
     * 注册账号
     */
    @RequestMapping("/registerSimple")
    //@RequiresPermissions("user:userbaseinfo:save")
    public R registerSimple(@RequestBody @Valid LoginOrRegisterSimpleDTO registerSimple){
        if (userBaseInfoService.register(registerSimple)){
            return R.ok("注册成功");
        }else {
            return R.error(ErrorCodeEnum.REGISTER_FAIL_EXCEPTION.getCode(), ErrorCodeEnum.REGISTER_FAIL_EXCEPTION.getMsg());
        }
    }
    /**
     * 检测用户名是否重复
     */
    @GetMapping("/checkUserName/{userName}")
    public R checkUserName(@PathVariable String userName){
        return R.ok().put("result",userBaseInfoService.checkUserName(userName));
    }
}
