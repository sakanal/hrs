package com.sakanal.house.controller;

import java.util.Arrays;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.sakanal.base.utils.PageUtils;
import com.sakanal.base.utils.R;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sakanal.service.entity.house.BaseFacilitiesEntity;
import com.sakanal.house.service.BaseFacilitiesService;

import javax.annotation.Resource;



/**
 * 房屋设施表
 *
 * @author sakanal
 * @email 1104820805@qq.com
 * @date 2023-02-03 22:03:33
 */
@RestController
@RequestMapping("house/basefacilities")
public class BaseFacilitiesController {
    @Resource
    private BaseFacilitiesService baseFacilitiesService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("house:basefacilities:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = baseFacilitiesService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("house:basefacilities:info")
    public R info(@PathVariable("id") Long id){
		BaseFacilitiesEntity baseFacilities = baseFacilitiesService.getById(id);

        return R.ok().put("baseFacilities", baseFacilities);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("house:basefacilities:save")
    public R save(@RequestBody BaseFacilitiesEntity baseFacilities){
		baseFacilitiesService.save(baseFacilities);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("house:basefacilities:update")
    public R update(@RequestBody BaseFacilitiesEntity baseFacilities){
		baseFacilitiesService.updateById(baseFacilities);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("house:basefacilities:delete")
    public R delete(@RequestBody Long[] ids){
		baseFacilitiesService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
