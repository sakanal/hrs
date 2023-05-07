package com.sakanal.house.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.sakanal.service.vo.OrientationVO;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

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
    @CacheEvict(value = {"orientation"},allEntries = true)
    //@RequiresPermissions("house:baseorientation:save")
    public R save(@RequestBody BaseOrientationEntity baseOrientation){
		baseOrientationService.save(baseOrientation);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @CacheEvict(value = {"orientation"},allEntries = true)
    //@RequiresPermissions("house:baseorientation:update")
    public R update(@RequestBody BaseOrientationEntity baseOrientation){
		baseOrientationService.updateById(baseOrientation);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @CacheEvict(value = {"orientation"},allEntries = true)
    //@RequiresPermissions("house:baseorientation:delete")
    public R delete(@RequestBody Long[] ids){
		baseOrientationService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    @GetMapping("/getAll")
    public R getAll(){
        List<OrientationVO> list = baseOrientationService.getAll();

        return R.ok().put("data",list);
    }

}
