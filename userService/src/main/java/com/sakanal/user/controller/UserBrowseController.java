package com.sakanal.user.controller;

import java.util.Arrays;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sakanal.user.entity.UserBrowseEntity;
import com.sakanal.user.service.UserBrowseService;
import com.sakanal.common.utils.PageUtils;
import com.sakanal.common.utils.R;

import javax.annotation.Resource;



/**
 * 用户浏览表
 *
 * @author sakanal
 * @email 1104820805@qq.com
 * @date 2023-02-22 13:24:10
 */
@RestController
@RequestMapping("user/userbrowse")
public class UserBrowseController {
    @Resource
    private UserBrowseService userBrowseService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("user:userbrowse:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = userBrowseService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("user:userbrowse:info")
    public R info(@PathVariable("id") Long id){
		UserBrowseEntity userBrowse = userBrowseService.getById(id);

        return R.ok().put("userBrowse", userBrowse);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("user:userbrowse:save")
    public R save(@RequestBody UserBrowseEntity userBrowse){
		userBrowseService.save(userBrowse);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("user:userbrowse:update")
    public R update(@RequestBody UserBrowseEntity userBrowse){
		userBrowseService.updateById(userBrowse);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("user:userbrowse:delete")
    public R delete(@RequestBody Long[] ids){
		userBrowseService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
