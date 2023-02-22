package com.sakanal.house.controller;

import java.util.Arrays;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sakanal.house.entity.HouseRentInfoEntity;
import com.sakanal.house.service.HouseRentInfoService;
import com.sakanal.base.utils.PageUtils;
import com.sakanal.base.utils.R;

import javax.annotation.Resource;



/**
 * 房源租金信息表
 *
 * @author sakanal
 * @email 1104820805@qq.com
 * @date 2023-02-03 22:03:33
 */
@RestController
@RequestMapping("house/houserentinfo")
public class HouseRentInfoController {
    @Resource
    private HouseRentInfoService houseRentInfoService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("house:houserentinfo:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = houseRentInfoService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("house:houserentinfo:info")
    public R info(@PathVariable("id") Long id){
		HouseRentInfoEntity houseRentInfo = houseRentInfoService.getById(id);

        return R.ok().put("houseRentInfo", houseRentInfo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("house:houserentinfo:save")
    public R save(@RequestBody HouseRentInfoEntity houseRentInfo){
		houseRentInfoService.save(houseRentInfo);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("house:houserentinfo:update")
    public R update(@RequestBody HouseRentInfoEntity houseRentInfo){
		houseRentInfoService.updateById(houseRentInfo);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("house:houserentinfo:delete")
    public R delete(@RequestBody Long[] ids){
		houseRentInfoService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
