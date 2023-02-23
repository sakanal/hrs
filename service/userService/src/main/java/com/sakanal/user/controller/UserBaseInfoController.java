package com.sakanal.user.controller;

import java.util.Arrays;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.sakanal.base.exception.ErrorCodeEnum;
import com.sakanal.service.dto.LoginOrRegisterSimpleDTO;
import com.sakanal.service.vo.UserBaseInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import com.sakanal.service.entity.user.UserBaseInfoEntity;
import com.sakanal.user.service.UserBaseInfoService;
import com.sakanal.base.utils.PageUtils;
import com.sakanal.base.utils.R;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


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
    @PostMapping("/loginSimple")
    public R loginSimple(@RequestBody @Valid LoginOrRegisterSimpleDTO loginSimple){
        String token = userBaseInfoService.login(loginSimple);
        if (StringUtils.hasText(token)){
            return R.ok().put("token",token);
        }else {
            return R.error(ErrorCodeEnum.LOGIN_FAIL_EXCEPTION.getCode(), ErrorCodeEnum.LOGIN_FAIL_EXCEPTION.getMsg());
        }
    }
    @GetMapping("/userInfoByToken")
    public R userInfoByToken(HttpServletRequest httpServletRequest,@RequestHeader(value = "token") String token){
        UserBaseInfoVO userBaseInfoVO = userBaseInfoService.getUserInfoByToken(token);
        if (userBaseInfoVO!=null){
            return R.ok().put("userInfo",userBaseInfoVO);
        }else {
            return R.error();
        }
    }

}
