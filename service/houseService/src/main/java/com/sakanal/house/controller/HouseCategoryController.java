package com.sakanal.house.controller;

import java.util.Arrays;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sakanal.house.entity.HouseCategoryEntity;
import com.sakanal.house.service.HouseCategoryService;
import com.sakanal.base.utils.PageUtils;
import com.sakanal.base.utils.R;

import javax.annotation.Resource;



/**
 * 房屋类别表
 *
 * @author sakanal
 * @email 1104820805@qq.com
 * @date 2023-02-03 22:03:33
 */
@RestController
@RequestMapping("house/housecategory")
public class HouseCategoryController {
    @Resource
    private HouseCategoryService houseCategoryService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("house:housecategory:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = houseCategoryService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("house:housecategory:info")
    public R info(@PathVariable("id") Long id){
		HouseCategoryEntity houseCategory = houseCategoryService.getById(id);

        return R.ok().put("houseCategory", houseCategory);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("house:housecategory:save")
    public R save(@RequestBody HouseCategoryEntity houseCategory){
		houseCategoryService.save(houseCategory);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("house:housecategory:update")
    public R update(@RequestBody HouseCategoryEntity houseCategory){
		houseCategoryService.updateById(houseCategory);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("house:housecategory:delete")
    public R delete(@RequestBody Long[] ids){
		houseCategoryService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
