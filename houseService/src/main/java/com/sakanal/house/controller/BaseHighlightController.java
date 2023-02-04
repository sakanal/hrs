package com.sakanal.house.controller;

import java.util.Arrays;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sakanal.house.entity.BaseHighlightEntity;
import com.sakanal.house.service.BaseHighlightService;
import com.sakanal.common.utils.PageUtils;
import com.sakanal.common.utils.R;

import javax.annotation.Resource;



/**
 * 房屋亮点表
 *
 * @author sakanal
 * @email 1104820805@qq.com
 * @date 2023-02-03 22:03:33
 */
@RestController
@RequestMapping("house/basehighlight")
public class BaseHighlightController {
    @Resource
    private BaseHighlightService baseHighlightService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("house:basehighlight:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = baseHighlightService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("house:basehighlight:info")
    public R info(@PathVariable("id") Long id){
		BaseHighlightEntity baseHighlight = baseHighlightService.getById(id);

        return R.ok().put("baseHighlight", baseHighlight);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("house:basehighlight:save")
    public R save(@RequestBody BaseHighlightEntity baseHighlight){
		baseHighlightService.save(baseHighlight);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("house:basehighlight:update")
    public R update(@RequestBody BaseHighlightEntity baseHighlight){
		baseHighlightService.updateById(baseHighlight);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("house:basehighlight:delete")
    public R delete(@RequestBody Long[] ids){
		baseHighlightService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
