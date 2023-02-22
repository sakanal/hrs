package com.sakanal.user.controller;

import java.util.Arrays;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sakanal.user.entity.BusinessPromotionEntity;
import com.sakanal.user.service.BusinessPromotionService;
import com.sakanal.base.utils.PageUtils;
import com.sakanal.base.utils.R;

import javax.annotation.Resource;



/**
 * 商家推广数据表
 *
 * @author sakanal
 * @email 1104820805@qq.com
 * @date 2023-02-22 13:24:10
 */
@RestController
@RequestMapping("user/businesspromotion")
public class BusinessPromotionController {
    @Resource
    private BusinessPromotionService businessPromotionService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("user:businesspromotion:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = businessPromotionService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("user:businesspromotion:info")
    public R info(@PathVariable("id") Long id){
		BusinessPromotionEntity businessPromotion = businessPromotionService.getById(id);

        return R.ok().put("businessPromotion", businessPromotion);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("user:businesspromotion:save")
    public R save(@RequestBody BusinessPromotionEntity businessPromotion){
		businessPromotionService.save(businessPromotion);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("user:businesspromotion:update")
    public R update(@RequestBody BusinessPromotionEntity businessPromotion){
		businessPromotionService.updateById(businessPromotion);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("user:businesspromotion:delete")
    public R delete(@RequestBody Long[] ids){
		businessPromotionService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
