package com.sakanal.house.controller;

import java.util.Arrays;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sakanal.base.constant.PublishStateConstant;
import org.springframework.web.bind.annotation.*;

import com.sakanal.service.entity.house.HouseStateEntity;
import com.sakanal.house.service.HouseStateService;
import com.sakanal.base.utils.PageUtils;
import com.sakanal.base.utils.R;

import javax.annotation.Resource;



/**
 * 房源状态表
 *
 * @author sakanal
 * @email 1104820805@qq.com
 * @date 2023-02-03 22:03:33
 */
@RestController
@RequestMapping("house/housestate")
public class HouseStateController {
    @Resource
    private HouseStateService houseStateService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("house:housestate:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = houseStateService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("house:housestate:info")
    public R info(@PathVariable("id") Long id){
		HouseStateEntity houseState = houseStateService.getById(id);

        return R.ok().put("houseState", houseState);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("house:housestate:save")
    public R save(@RequestBody HouseStateEntity houseState){
		houseStateService.save(houseState);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("house:housestate:update")
    public R update(@RequestBody HouseStateEntity houseState){
		houseStateService.updateById(houseState);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("house:housestate:delete")
    public R delete(@RequestBody Long[] ids){
		houseStateService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    @DeleteMapping("/setHouseOff/{houseBaseInfoId}")
    public R setHouseOff(@PathVariable Long houseBaseInfoId){
        HouseStateEntity houseStateEntity = new HouseStateEntity();
        houseStateEntity.setHousePublishState(PublishStateConstant.PUBLISH_END_STATE);
        boolean update = houseStateService.update(houseStateEntity, new LambdaQueryWrapper<HouseStateEntity>().eq(HouseStateEntity::getBaseInfoId, houseBaseInfoId));
        return R.ok().put("data",update);
    }

}
