package com.sakanal.user.feign;

import com.sakanal.base.utils.R;
import com.sakanal.service.dto.PublishInfoListDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Service
@FeignClient(value = "houseService")
public interface HouseFeignClient {
    @PutMapping("/house/housecontactinfo/updatePublisherNameByUserId")
    public R updatePublisherNameByUserId(@RequestBody Map<String, Object> params);

    @PostMapping("/house/houseInfo/getPublishInfoList")
    public R getPublishInfoList(@RequestBody PublishInfoListDTO publishInfoListDTO);
    @PostMapping("/house/houseInfo/getBrowsePublishInfoList")
    public R getBrowsePublishInfoList(@RequestBody PublishInfoListDTO publishInfoListDTO);
}
