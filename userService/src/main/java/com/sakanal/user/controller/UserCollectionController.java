package com.sakanal.user.controller;

import java.util.Arrays;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sakanal.user.entity.UserCollectionEntity;
import com.sakanal.user.service.UserCollectionService;
import com.sakanal.common.utils.PageUtils;
import com.sakanal.common.utils.R;

import javax.annotation.Resource;



/**
 * 用户收藏表
 *
 * @author sakanal
 * @email 1104820805@qq.com
 * @date 2023-02-22 13:24:10
 */
@RestController
@RequestMapping("user/usercollection")
public class UserCollectionController {
    @Resource
    private UserCollectionService userCollectionService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("user:usercollection:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = userCollectionService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("user:usercollection:info")
    public R info(@PathVariable("id") Long id){
		UserCollectionEntity userCollection = userCollectionService.getById(id);

        return R.ok().put("userCollection", userCollection);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("user:usercollection:save")
    public R save(@RequestBody UserCollectionEntity userCollection){
		userCollectionService.save(userCollection);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("user:usercollection:update")
    public R update(@RequestBody UserCollectionEntity userCollection){
		userCollectionService.updateById(userCollection);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("user:usercollection:delete")
    public R delete(@RequestBody Long[] ids){
		userCollectionService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
