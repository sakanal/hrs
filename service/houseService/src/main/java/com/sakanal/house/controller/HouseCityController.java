package com.sakanal.house.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sakanal.service.vo.CityWithPinyinVO;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import com.sakanal.service.entity.house.HouseCityEntity;
import com.sakanal.house.service.HouseCityService;
import com.sakanal.base.utils.PageUtils;
import com.sakanal.base.utils.R;

import javax.annotation.Resource;



/**
 * 房源所在城市表
 *
 * @author sakanal
 * @email 1104820805@qq.com
 * @date 2023-02-03 22:03:33
 */
@RestController
@RequestMapping("house/housecity")
public class HouseCityController {
    @Resource
    private HouseCityService houseCityService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("house:housecity:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = houseCityService.queryPage(params);

        return R.ok().put("page", page);
    }
    @PostMapping("/childrenList")
    public R childrenList(){
        List<HouseCityEntity> list = houseCityService.childrenList();

        return R.ok().put("data",list);
    }
    @GetMapping("/getSuperiorIds/{id}")
    public R getSuperiorIds(@PathVariable("id") Long cityId){
        List<Long> superiorIds = houseCityService.getRelatedSuperiorIdsById(cityId);

        return R.ok().put("data",superiorIds);
    }
    @GetMapping("/childrenListWithPinyin")
    public R childrenListWithPinyin(){
        List<CityWithPinyinVO> list = houseCityService.childrenWithPinyinList();
        return R.ok().put("data",list);
    }
    @GetMapping("/getCityIdByCityName/{cityName}")
    public  R getCityIdByCityName(@PathVariable String cityName){
        // 根据城市名获取城市id
        Long id = houseCityService.list(new LambdaQueryWrapper<HouseCityEntity>().select(HouseCityEntity::getId).like(HouseCityEntity::getName, cityName).last("limit 1")).get(0).getId();
        return R.ok().put("data",id);
    }
    @GetMapping("/getNextChildrenListByCityId/{cityId}")
    public R getNextChildrenListByCityId(@PathVariable Long cityId){
        List<HouseCityEntity> list = houseCityService.list(new LambdaQueryWrapper<HouseCityEntity>()
                .select(HouseCityEntity::getId,HouseCityEntity::getName)
                .eq(HouseCityEntity::getSuperiorId, cityId));
        return R.ok().put("data",list);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("house:housecity:info")
    public R info(@PathVariable("id") Long id){
		HouseCityEntity houseCity = houseCityService.getById(id);

        return R.ok().put("houseCity", houseCity);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @CacheEvict(value = {"houseCity"},allEntries = true)
    //@RequiresPermissions("house:housecity:save")
    public R save(@RequestBody HouseCityEntity houseCity){
		houseCityService.save(houseCity);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @CacheEvict(value = {"houseCity"},allEntries = true)
    //@RequiresPermissions("house:housecity:update")
    public R update(@RequestBody HouseCityEntity houseCity){
		houseCityService.updateById(houseCity);
        // TODO 如果城市名称修改，需要将其他和城市名称有关的数据进行修改，eg：HouseArea里面的areaAddress会使用城市名称

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @CacheEvict(value = {"houseCity"},allEntries = true)
    //@RequiresPermissions("house:housecity:delete")
    public R delete(@RequestBody Long[] ids){
		houseCityService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
