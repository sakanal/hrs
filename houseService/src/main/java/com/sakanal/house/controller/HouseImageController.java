package com.sakanal.house.controller;

import java.util.Arrays;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sakanal.house.entity.HouseImageEntity;
import com.sakanal.house.service.HouseImageService;
import com.sakanal.common.utils.PageUtils;
import com.sakanal.common.utils.R;

import javax.annotation.Resource;



/**
 * 房源图片表
 *
 * @author sakanal
 * @email 1104820805@qq.com
 * @date 2023-02-03 22:03:33
 */
@RestController
@RequestMapping("house/houseimage")
public class HouseImageController {
    @Resource
    private HouseImageService houseImageService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("house:houseimage:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = houseImageService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("house:houseimage:info")
    public R info(@PathVariable("id") Long id){
		HouseImageEntity houseImage = houseImageService.getById(id);

        return R.ok().put("houseImage", houseImage);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("house:houseimage:save")
    public R save(@RequestBody HouseImageEntity houseImage){
		houseImageService.save(houseImage);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("house:houseimage:update")
    public R update(@RequestBody HouseImageEntity houseImage){
		houseImageService.updateById(houseImage);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("house:houseimage:delete")
    public R delete(@RequestBody Long[] ids){
		houseImageService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
