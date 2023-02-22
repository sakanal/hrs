package com.sakanal.house.controller;

import java.util.Arrays;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sakanal.house.entity.HouseDetailedInfoEntity;
import com.sakanal.house.service.HouseDetailedInfoService;
import com.sakanal.base.utils.PageUtils;
import com.sakanal.base.utils.R;

import javax.annotation.Resource;



/**
 * 房源详细介绍表
 *
 * @author sakanal
 * @email 1104820805@qq.com
 * @date 2023-02-05 12:48:19
 */
@RestController
@RequestMapping("house/housedetailedinfo")
public class HouseDetailedInfoController {
    @Resource
    private HouseDetailedInfoService houseDetailedInfoService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("house:housedetailedinfo:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = houseDetailedInfoService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("house:housedetailedinfo:info")
    public R info(@PathVariable("id") Long id){
		HouseDetailedInfoEntity houseDetailedInfo = houseDetailedInfoService.getById(id);

        return R.ok().put("houseDetailedInfo", houseDetailedInfo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("house:housedetailedinfo:save")
    public R save(@RequestBody HouseDetailedInfoEntity houseDetailedInfo){
		houseDetailedInfoService.save(houseDetailedInfo);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("house:housedetailedinfo:update")
    public R update(@RequestBody HouseDetailedInfoEntity houseDetailedInfo){
		houseDetailedInfoService.updateById(houseDetailedInfo);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("house:housedetailedinfo:delete")
    public R delete(@RequestBody Long[] ids){
		houseDetailedInfoService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
