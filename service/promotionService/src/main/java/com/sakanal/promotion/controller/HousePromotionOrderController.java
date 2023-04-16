package com.sakanal.promotion.controller;

import java.util.Arrays;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.sakanal.base.utils.PageUtils;
import com.sakanal.base.utils.R;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sakanal.service.entity.promotion.HousePromotionOrderEntity;
import com.sakanal.promotion.service.HousePromotionOrderService;

import javax.annotation.Resource;



/**
 * 推广活动订单表
 *
 * @author sakanal
 * @email 1104820805@qq.com
 * @date 2023-04-16 10:41:39
 */
@RestController
@RequestMapping("promotion/housepromotionorder")
public class HousePromotionOrderController {
    @Resource
    private HousePromotionOrderService housePromotionOrderService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("promotion:housepromotionorder:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = housePromotionOrderService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("promotion:housepromotionorder:info")
    public R info(@PathVariable("id") Long id){
		HousePromotionOrderEntity housePromotionOrder = housePromotionOrderService.getById(id);

        return R.ok().put("housePromotionOrder", housePromotionOrder);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("promotion:housepromotionorder:save")
    public R save(@RequestBody HousePromotionOrderEntity housePromotionOrder){
		housePromotionOrderService.save(housePromotionOrder);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("promotion:housepromotionorder:update")
    public R update(@RequestBody HousePromotionOrderEntity housePromotionOrder){
		housePromotionOrderService.updateById(housePromotionOrder);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("promotion:housepromotionorder:delete")
    public R delete(@RequestBody Long[] ids){
		housePromotionOrderService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
