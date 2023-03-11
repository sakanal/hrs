package com.sakanal.house.controller;

import com.sakanal.base.exception.ErrorCodeEnum;
import com.sakanal.base.utils.PageUtils;
import com.sakanal.base.utils.R;
import com.sakanal.house.service.HouseBaseInfoService;
import com.sakanal.service.dto.PublishInfoDTO;
import com.sakanal.service.entity.house.HouseBaseInfoEntity;
import com.sakanal.service.vo.CityWithAreaVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Map;



/**
 * 房屋基础信息表
 *
 * @author sakanal
 * @email 1104820805@qq.com
 * @date 2023-02-03 22:03:33
 */
@RestController
@RequestMapping("house/housebaseinfo")
public class HouseBaseInfoController {
    @Resource
    private HouseBaseInfoService houseBaseInfoService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("house:housebaseinfo:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = houseBaseInfoService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("house:housebaseinfo:info")
    public R info(@PathVariable("id") Long id){
		HouseBaseInfoEntity houseBaseInfo = houseBaseInfoService.getById(id);

        return R.ok().put("houseBaseInfo", houseBaseInfo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("house:housebaseinfo:save")
    public R save(@RequestBody HouseBaseInfoEntity houseBaseInfo){
		houseBaseInfoService.save(houseBaseInfo);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("house:housebaseinfo:update")
    public R update(@RequestBody HouseBaseInfoEntity houseBaseInfo){
		houseBaseInfoService.updateById(houseBaseInfo);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("house:housebaseinfo:delete")
    public R delete(@RequestBody Long[] ids){
		houseBaseInfoService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    @GetMapping("/getThirdCity/{cityId}")
    public R getThirdCity(@PathVariable("cityId")Long cityId){
        CityWithAreaVO publishBaseInfoVO = houseBaseInfoService.getPublishBaseInfo(cityId);

        return R.ok().put("data",publishBaseInfoVO);
    }

    @PostMapping("/submitPublishBaseInfo")
    public R submitPublishBaseInfo(@RequestBody PublishInfoDTO publishInfoDTO) {
        if (houseBaseInfoService.submitPublish(publishInfoDTO)) {
            return R.ok();
        } else {
            return R.error(ErrorCodeEnum.PUBLISH_FAIL_EXCEPTION.getCode(), ErrorCodeEnum.PUBLISH_FAIL_EXCEPTION.getMsg());
        }

    }

    @GetMapping("/getPublishInfoList/{publishId}")
    public R getPublishInfoList(@PathVariable("publishId") Long publishId,
                                @RequestParam(value = "state", required = false) Integer state,
                                @RequestParam(value = "current", defaultValue = "1") Integer currentPage) {
        PageUtils page = houseBaseInfoService.getPublishInfoList(publishId, state, currentPage);

        return R.ok().put("page",page);
    }
}
