package com.sakanal.house.controller;

import com.sakanal.base.exception.ErrorCodeEnum;
import com.sakanal.base.utils.PageUtils;
import com.sakanal.base.utils.R;
import com.sakanal.house.service.HouseService;
import com.sakanal.service.dto.PublishInfoDTO;
import com.sakanal.service.dto.PublishInfoListDTO;
import com.sakanal.service.dto.RecommendInfoListDTO;
import com.sakanal.service.vo.PublishInfoVO;
import com.sakanal.service.vo.RecommendInfoVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


/**
 * 房屋信息表
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
    public R updatePublishBaseInfo(@RequestBody PublishInfoDTO publishInfoDTO) {
        if (houseService.updatePublish(publishInfoDTO)) {
            return R.ok();
        } else {
            return R.error(ErrorCodeEnum.UPDATE_FAIL_EXCEPTION.getCode(), ErrorCodeEnum.UPDATE_FAIL_EXCEPTION.getMsg());
        }
    }


    /**
     * 根据条件获取粗略数据
     */
    @PostMapping("/getPublishInfoList")
    public R getPublishInfoList(@RequestBody PublishInfoListDTO publishInfoListDTO) {
        PageUtils page = houseService.getPublishInfoList(publishInfoListDTO);

        return R.ok().put("page", page);
    }

    /**
     * 获取推荐房源
     */
    @PostMapping("/getRecommendListInIndex")
    public R getRecommendListInIndex(@RequestBody RecommendInfoListDTO recommendInfoListDTO) {
        /*
         * cityId--首页根据城市推荐-省级
         * childrenCityQuery--首页根据区域推荐-县级
         * roadId--首页选中道路后，根据道路推荐 or 详细页中根据道路推荐
         *
         */
        List<RecommendInfoVO> list = houseService.getRecommendList(recommendInfoListDTO);

        return R.ok().put("data", list);
    }

    @PostMapping("/getRecommendListInHouseInfo/{baseInfoId}")
    public R getRecommendListInHouseInfo(@PathVariable Long baseInfoId) {
        List<RecommendInfoVO> list = houseService.getRecommendList(baseInfoId);

        return R.ok().put("data", list);
    }

    /**
     * 根据房源基本信息id获取更新所需数据，即以id为主而不是具体名字
     */
    @GetMapping("/getUpdatePublishInfo/{houseBaseInfoId}")
    public R getUpdatePublishInfo(@PathVariable("houseBaseInfoId") Long houseBaseInfoId) {
        PublishInfoVO publishInfoVO = houseService.getUpdatePublishInfo(houseBaseInfoId);
        return R.ok().put("data", publishInfoVO);
    }

    /**
     * 根据房源基本信息id获取有关所有的数据，用于前端展示
     */
    @GetMapping("/getPublishInfo/{houseBaseInfoId}")
    public R getPublishInfo(@PathVariable("houseBaseInfoId") Long houseBaseInfoId) {
        PublishInfoVO publishInfoVO = houseService.getPublishInfo(houseBaseInfoId);
        return R.ok().put("data", publishInfoVO);
    }

    @PutMapping("/changePublishState/{houseBaseInfoId}/{state}")
    public R changePublishState(@PathVariable Long houseBaseInfoId,
                                @PathVariable Integer state) {
        if (houseService.changePublishState(houseBaseInfoId, state)) {
            return R.ok();
        }
        return R.error();
    }
}
