package com.sakanal.user.controller;

import java.util.Arrays;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sakanal.user.entity.BusinessHousePublishEntity;
import com.sakanal.user.service.BusinessHousePublishService;
import com.sakanal.common.utils.PageUtils;
import com.sakanal.common.utils.R;

import javax.annotation.Resource;



/**
 * 商家房源发布表
 *
 * @author sakanal
 * @email 1104820805@qq.com
 * @date 2023-02-22 13:24:10
 */
@RestController
@RequestMapping("user/businesshousepublish")
public class BusinessHousePublishController {
    @Resource
    private BusinessHousePublishService businessHousePublishService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("user:businesshousepublish:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = businessHousePublishService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("user:businesshousepublish:info")
    public R info(@PathVariable("id") Long id){
		BusinessHousePublishEntity businessHousePublish = businessHousePublishService.getById(id);

        return R.ok().put("businessHousePublish", businessHousePublish);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("user:businesshousepublish:save")
    public R save(@RequestBody BusinessHousePublishEntity businessHousePublish){
		businessHousePublishService.save(businessHousePublish);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("user:businesshousepublish:update")
    public R update(@RequestBody BusinessHousePublishEntity businessHousePublish){
		businessHousePublishService.updateById(businessHousePublish);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("user:businesshousepublish:delete")
    public R delete(@RequestBody Long[] ids){
		businessHousePublishService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
