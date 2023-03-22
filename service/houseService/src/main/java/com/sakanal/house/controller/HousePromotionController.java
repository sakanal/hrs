package com.sakanal.house.controller;

import com.sakanal.base.utils.PageUtils;
import com.sakanal.base.utils.R;
import com.sakanal.house.entity.HousePromotionEntity;
import com.sakanal.house.service.HousePromotionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Map;


/**
 * 房源推广数据表
 *
 * @author sakanal
 * @email 1104820805@qq.com
 * @date 2023-03-22 16:19:43
 */
@RestController
@RequestMapping("house/housepromotion")
public class HousePromotionController {
    @Resource
    private HousePromotionService housePromotionService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("house:housepromotion:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = housePromotionService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{baseInfoId}")
    //@RequiresPermissions("house:housepromotion:info")
    public R info(@PathVariable("baseInfoId") Long baseInfoId) {
        HousePromotionEntity housePromotion = housePromotionService.getById(baseInfoId);

        return R.ok().put("housePromotion", housePromotion);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("house:housepromotion:save")
    public R save(@RequestBody HousePromotionEntity housePromotion) {
        housePromotionService.save(housePromotion);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("house:housepromotion:update")
    public R update(@RequestBody HousePromotionEntity housePromotion) {
        housePromotionService.updateById(housePromotion);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("house:housepromotion:delete")
    public R delete(@RequestBody Long[] baseInfoIds) {
        housePromotionService.removeByIds(Arrays.asList(baseInfoIds));

        return R.ok();
    }

}
