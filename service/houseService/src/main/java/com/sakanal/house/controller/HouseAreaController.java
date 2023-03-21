package com.sakanal.house.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sakanal.base.constant.AreaLevelConstant;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import com.sakanal.service.entity.house.HouseAreaEntity;
import com.sakanal.house.service.HouseAreaService;
import com.sakanal.base.utils.PageUtils;
import com.sakanal.base.utils.R;

import javax.annotation.Resource;



/**
 * 房屋区域表
 *
 * @author sakanal
 * @email 1104820805@qq.com
 * @date 2023-02-03 22:03:33
 */
@RestController
@RequestMapping("house/housearea")
public class HouseAreaController {
    @Resource
    private HouseAreaService houseAreaService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("house:housearea:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = houseAreaService.queryPage(params);

        return R.ok().put("page", page);
    }
    @PostMapping("/childrenList")
    public R childrenList(@RequestBody HouseAreaEntity houseAreaEntity){
        List<HouseAreaEntity> list = houseAreaService.childrenList(houseAreaEntity.getCityId());

        return R.ok().put("data",list);
    }
    @GetMapping("/getRoadListByCityId/{cityId}")
    public R getRoadListByCityId(@PathVariable Long cityId){
        List<HouseAreaEntity> list = houseAreaService.list(new LambdaQueryWrapper<HouseAreaEntity>()
                .eq(HouseAreaEntity::getCityId, cityId)
                .eq(HouseAreaEntity::getLevel, AreaLevelConstant.FIRST)
        );
        return R.ok().put("data",list);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("house:housearea:info")
    public R info(@PathVariable("id") Long id){
		HouseAreaEntity houseArea = houseAreaService.getById(id);

        return R.ok().put("houseArea", houseArea);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @CacheEvict(value = {"houseArea"},allEntries = true)
    //@RequiresPermissions("house:housearea:save")
    public R save(@RequestBody HouseAreaEntity houseArea){
		houseAreaService.save(houseArea);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @CacheEvict(value = {"houseArea"},allEntries = true)
    //@RequiresPermissions("house:housearea:update")
    public R update(@RequestBody HouseAreaEntity houseArea){
		houseAreaService.updateById(houseArea);

        return R.ok();
    }
    @PutMapping("/update/showState")
    public R updateShowState(@RequestBody HouseAreaEntity houseAreaEntity){
        if (houseAreaService.updateShowStateById(houseAreaEntity)){
            return R.ok();
        }else {
            return R.error();
        }
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @CacheEvict(value = {"houseArea"},allEntries = true)
    //@RequiresPermissions("house:housearea:delete")
    public R delete(@RequestBody Long[] ids){
		houseAreaService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
