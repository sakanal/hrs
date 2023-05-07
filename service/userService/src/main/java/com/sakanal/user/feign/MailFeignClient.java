package com.sakanal.user.feign;

import com.sakanal.service.dto.EmailCodeDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
@FeignClient(value = "thirdParty", contextId = "MAIL")
public interface MailFeignClient {

    @RequestMapping("/thirdParty/Mail/send")
    String send(@RequestBody EmailCodeDTO emailCodeDTO);
}
