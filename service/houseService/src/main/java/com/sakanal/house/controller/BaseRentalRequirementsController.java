package com.sakanal.house.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.sakanal.service.vo.RentalRequirementsVO;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import com.sakanal.service.entity.house.BaseRentalRequirementsEntity;
import com.sakanal.house.service.BaseRentalRequirementsService;
import com.sakanal.base.utils.PageUtils;
import com.sakanal.base.utils.R;

import javax.annotation.Resource;



/**
 * 房屋出租要求表
 *
 * @author sakanal
 * @email 1104820805@qq.com
 * @date 2023-02-03 22:03:33
 */
@RestController
@RequestMapping("house/baserentalrequirements")
public class BaseRentalRequirementsController {
    @Resource
    private BaseRentalRequirementsService baseRentalRequirementsService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("house:baserentalrequirements:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = baseRentalRequirementsService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("house:baserentalrequirements:info")
    public R info(@PathVariable("id") Long id){
		BaseRentalRequirementsEntity baseRentalRequirements = baseRentalRequirementsService.getById(id);

        return R.ok().put("baseRentalRequirements", baseRentalRequirements);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @CacheEvict(value = {"rentalRequirements"},allEntries = true)
    //@RequiresPermissions("house:baserentalrequirements:save")
    public R save(@RequestBody BaseRentalRequirementsEntity baseRentalRequirements){
		baseRentalRequirementsService.save(baseRentalRequirements);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @CacheEvict(value = {"rentalRequirements"},allEntries = true)
    //@RequiresPermissions("house:baserentalrequirements:update")
    public R update(@RequestBody BaseRentalRequirementsEntity baseRentalRequirements){
		baseRentalRequirementsService.updateById(baseRentalRequirements);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @CacheEvict(value = {"rentalRequirements"},allEntries = true)
    //@RequiresPermissions("house:baserentalrequirements:delete")
    public R delete(@RequestBody Long[] ids){
		baseRentalRequirementsService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }


    @GetMapping("/getAll")
    public R getAll(){
        List<RentalRequirementsVO> list = baseRentalRequirementsService.getAll();

        return R.ok().put("data",list);
    }

}
