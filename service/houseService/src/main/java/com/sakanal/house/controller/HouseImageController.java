package com.sakanal.house.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sakanal.base.utils.PageUtils;
import com.sakanal.base.utils.R;
import com.sakanal.house.service.HouseImageService;
import com.sakanal.service.dto.ImageDeleteDTO;
import com.sakanal.service.entity.house.HouseImageEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;



/**
 * 房源图片表
 *
 * @author sakanal
 * @email 1104820805@qq.com
 * @date 2023-02-03 22:03:33
 */
@RestController
@RequestMapping("house/houseimage")
public class HouseImageController {
    @Resource
    private HouseImageService houseImageService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("house:houseimage:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = houseImageService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("house:houseimage:info")
    public R info(@PathVariable("id") Long id){
		HouseImageEntity houseImage = houseImageService.getById(id);

        return R.ok().put("houseImage", houseImage);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("house:houseimage:save")
    public R save(@RequestBody HouseImageEntity houseImage){
		houseImageService.save(houseImage);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("house:houseimage:update")
    public R update(@RequestBody HouseImageEntity houseImage){
		houseImageService.updateById(houseImage);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("house:houseimage:delete")
    public R delete(@RequestBody ImageDeleteDTO imageDeleteDTO) {
        if (imageDeleteDTO!=null){
            houseImageService.remove(new LambdaQueryWrapper<HouseImageEntity>()
                    .eq(HouseImageEntity::getName, imageDeleteDTO.getName())
                    .eq(HouseImageEntity::getUrl, imageDeleteDTO.getUrl()));
        }
        return R.ok();
    }

}
