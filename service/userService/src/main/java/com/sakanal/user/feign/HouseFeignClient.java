package com.sakanal.user.feign;

import com.sakanal.base.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Service
@FeignClient(value = "houseService")
public interface HouseFeignClient {
    @PutMapping("/house/housecontactinfo/updatePublisherNameByUserId")
    public R updatePublisherNameByUserId(@RequestBody Map<String, Object> params);
}
