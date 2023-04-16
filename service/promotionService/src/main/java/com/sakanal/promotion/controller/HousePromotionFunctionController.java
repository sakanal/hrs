package com.sakanal.promotion.controller;

import com.sakanal.base.utils.PageUtils;
import com.sakanal.base.utils.R;
import com.sakanal.service.entity.promotion.HousePromotionFunctionEntity;
import com.sakanal.promotion.service.HousePromotionFunctionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Map;


/**
 * 推广活动表
 *
 * @author sakanal
 * @email 1104820805@qq.com
 * @date 2023-04-16 10:41:39
 */
@RestController
@RequestMapping("promotion/housepromotionfunction")
public class HousePromotionFunctionController {
    @Resource
    private HousePromotionFunctionService housePromotionFunctionService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("promotion:housepromotionfunction:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = housePromotionFunctionService.queryPage(params);

        return R.ok().put("page", page);
    }
    @PutMapping("/changeState/{promotionId}/{showState}")
    public R changeState(@PathVariable Long promotionId,
                         @PathVariable Integer showState){
        if(housePromotionFunctionService.changeState(promotionId,showState)){
            return R.ok();
        }else {
            return R.error();
        }
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("promotion:housepromotionfunction:info")
    public R info(@PathVariable("id") Long id) {
        HousePromotionFunctionEntity housePromotionFunction = housePromotionFunctionService.getById(id);

        return R.ok().put("housePromotionFunction", housePromotionFunction);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("promotion:housepromotionfunction:save")
    public R save(@RequestBody HousePromotionFunctionEntity housePromotionFunction) {
        housePromotionFunctionService.save(housePromotionFunction);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("promotion:housepromotionfunction:update")
    public R update(@RequestBody HousePromotionFunctionEntity housePromotionFunction) {
        housePromotionFunctionService.updateById(housePromotionFunction);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("promotion:housepromotionfunction:delete")
    public R delete(@RequestBody Long[] ids) {
        housePromotionFunctionService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
