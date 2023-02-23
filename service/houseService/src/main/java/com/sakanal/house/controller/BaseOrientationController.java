package com.sakanal.house.controller;

import java.util.Arrays;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sakanal.service.entity.house.BaseOrientationEntity;
import com.sakanal.house.service.BaseOrientationService;
import com.sakanal.base.utils.PageUtils;
import com.sakanal.base.utils.R;

import javax.annotation.Resource;



/**
 * 房屋朝向表
 *
 * @author sakanal
 * @email 1104820805@qq.com
 * @date 2023-02-03 22:03:33
 */
@RestController
@RequestMapping("house/baseorientation")
public class BaseOrientationController {
    @Resource
    private BaseOrientationService baseOrientationService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("house:baseorientation:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = baseOrientationService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("house:baseorientation:info")
    public R info(@PathVariable("id") Long id){
		BaseOrientationEntity baseOrientation = baseOrientationService.getById(id);

        return R.ok().put("baseOrientation", baseOrientation);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("house:baseorientation:save")
    public R save(@RequestBody BaseOrientationEntity baseOrientation){
		baseOrientationService.save(baseOrientation);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("house:baseorientation:update")
    public R update(@RequestBody BaseOrientationEntity baseOrientation){
		baseOrientationService.updateById(baseOrientation);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("house:baseorientation:delete")
    public R delete(@RequestBody Long[] ids){
		baseOrientationService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
