package com.sakanal.house.controller;

import java.util.Arrays;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import com.sakanal.service.entity.house.HouseContactInfoEntity;
import com.sakanal.house.service.HouseContactInfoService;
import com.sakanal.base.utils.PageUtils;
import com.sakanal.base.utils.R;

import javax.annotation.Resource;



/**
 * 房源联系电话表
 *
 * @author sakanal
 * @email 1104820805@qq.com
 * @date 2023-02-03 22:03:33
 */
@RestController
@RequestMapping("house/housecontactinfo")
public class HouseContactInfoController {
    @Resource
    private HouseContactInfoService houseContactInfoService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("house:housecontactinfo:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = houseContactInfoService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("house:housecontactinfo:info")
    public R info(@PathVariable("id") Long id){
		HouseContactInfoEntity houseContactInfo = houseContactInfoService.getById(id);

        return R.ok().put("houseContactInfo", houseContactInfo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("house:housecontactinfo:save")
    public R save(@RequestBody HouseContactInfoEntity houseContactInfo){
		houseContactInfoService.save(houseContactInfo);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("house:housecontactinfo:update")
    public R update(@RequestBody HouseContactInfoEntity houseContactInfo){
		houseContactInfoService.updateById(houseContactInfo);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("house:housecontactinfo:delete")
    public R delete(@RequestBody Long[] ids){
		houseContactInfoService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    @PutMapping("/updatePublisherNameByUserId")
    public R updatePublisherNameByUserId(@RequestBody Map<String, Object> params){
        if(houseContactInfoService.updatePublisherNameByUserId(params)){
            return R.ok();
        }else {
            return R.error();
        }
    }

}
