package com.sakanal.house.controller;

import java.util.Arrays;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sakanal.house.entity.BaseRentContentEntity;
import com.sakanal.house.service.BaseRentContentService;
import com.sakanal.base.utils.PageUtils;
import com.sakanal.base.utils.R;

import javax.annotation.Resource;



/**
 * 房屋租金内容表
 *
 * @author sakanal
 * @email 1104820805@qq.com
 * @date 2023-02-03 22:03:33
 */
@RestController
@RequestMapping("house/baserentcontent")
public class BaseRentContentController {
    @Resource
    private BaseRentContentService baseRentContentService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("house:baserentcontent:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = baseRentContentService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("house:baserentcontent:info")
    public R info(@PathVariable("id") Long id){
		BaseRentContentEntity baseRentContent = baseRentContentService.getById(id);

        return R.ok().put("baseRentContent", baseRentContent);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("house:baserentcontent:save")
    public R save(@RequestBody BaseRentContentEntity baseRentContent){
		baseRentContentService.save(baseRentContent);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("house:baserentcontent:update")
    public R update(@RequestBody BaseRentContentEntity baseRentContent){
		baseRentContentService.updateById(baseRentContent);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("house:baserentcontent:delete")
    public R delete(@RequestBody Long[] ids){
		baseRentContentService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
