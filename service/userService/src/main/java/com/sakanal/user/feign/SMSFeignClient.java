package com.sakanal.user.feign;

import com.sakanal.base.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
@FeignClient(value = "thirdParty",contextId = "SMS")
public interface SMSFeignClient {

    @RequestMapping("/thirdParty/SMS/send/{phone}/{code}")
    R send(@PathVariable("code") String code,
           @PathVariable("phone") String phone);
}
