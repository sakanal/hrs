package com.sakanal.house.controller;

import java.util.Arrays;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sakanal.house.entity.HouseBaseInfoEntity;
import com.sakanal.house.service.HouseBaseInfoService;
import com.sakanal.common.utils.PageUtils;
import com.sakanal.common.utils.R;

import javax.annotation.Resource;



/**
 * 房屋基础信息表
 *
 * @author sakanal
 * @email 1104820805@qq.com
 * @date 2023-02-03 22:03:33
 */
@RestController
@RequestMapping("house/housebaseinfo")
public class HouseBaseInfoController {
    @Resource
    private HouseBaseInfoService houseBaseInfoService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("house:housebaseinfo:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = houseBaseInfoService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("house:housebaseinfo:info")
    public R info(@PathVariable("id") Long id){
		HouseBaseInfoEntity houseBaseInfo = houseBaseInfoService.getById(id);

        return R.ok().put("houseBaseInfo", houseBaseInfo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("house:housebaseinfo:save")
    public R save(@RequestBody HouseBaseInfoEntity houseBaseInfo){
		houseBaseInfoService.save(houseBaseInfo);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("house:housebaseinfo:update")
    public R update(@RequestBody HouseBaseInfoEntity houseBaseInfo){
		houseBaseInfoService.updateById(houseBaseInfo);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("house:housebaseinfo:delete")
    public R delete(@RequestBody Long[] ids){
		houseBaseInfoService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
