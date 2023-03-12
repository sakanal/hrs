package com.sakanal.house.controller;

import com.sakanal.base.exception.ErrorCodeEnum;
import com.sakanal.base.utils.PageUtils;
import com.sakanal.base.utils.R;
import com.sakanal.house.service.HouseBaseInfoService;
import com.sakanal.house.service.HouseService;
import com.sakanal.service.dto.PublishInfoDTO;
import com.sakanal.service.entity.house.HouseBaseInfoEntity;
import com.sakanal.service.vo.CityWithAreaVO;
import com.sakanal.service.vo.PublishInfoVO;
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
@RequestMapping("house/houseInfo")
public class HouseInfoController {
    @Resource
    private HouseService houseService;

    @PostMapping("/submitPublishBaseInfo")
    public R submitPublishBaseInfo(@RequestBody PublishInfoDTO publishInfoDTO) {
        if (houseService.submitPublish(publishInfoDTO)) {
            return R.ok();
        } else {
            return R.error(ErrorCodeEnum.PUBLISH_FAIL_EXCEPTION.getCode(), ErrorCodeEnum.PUBLISH_FAIL_EXCEPTION.getMsg());
        }

    }

    @PostMapping("/updatePublishBaseInfo")
    public R updatePublishBaseInfo(@RequestBody PublishInfoDTO publishInfoDTO){
        if (houseService.updatePublish(publishInfoDTO)){
            return R.ok();
        }else {
            return R.error(ErrorCodeEnum.UPDATE_FAIL_EXCEPTION.getCode(), ErrorCodeEnum.UPDATE_FAIL_EXCEPTION.getMsg());
        }
    }

    @GetMapping("/getPublishInfoList/{publishId}")
    public R getPublishInfoList(@PathVariable("publishId") Long publishId,
                                @RequestParam(value = "state", required = false) Integer state,
                                @RequestParam(value = "current", defaultValue = "1") Integer currentPage) {
        PageUtils page = houseService.getPublishInfoList(publishId, state, currentPage);

        return R.ok().put("page",page);
    }
    /**
     * 根据房源基本信息id获取有关所有的数据
     */
    @GetMapping("/getPublishInfo/{houseBaseInfoId}")
    public R getPublishInfo(@PathVariable("houseBaseInfoId") String houseBaseInfoId){
        PublishInfoVO publishInfoVO = houseService.getPublishInfo(houseBaseInfoId);
        return R.ok().put("data",publishInfoVO);
    }
}
