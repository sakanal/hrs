package com.sakanal.promotion.feign;

import com.sakanal.base.utils.R;
import com.sakanal.service.dto.PayDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@Service
@FeignClient(value = "thirdParty")
public interface AliPayFeignClient {
    @PostMapping("/thirdParty/AliPay/simplePay")
    public R simplePay(@RequestBody PayDTO payDTO);
}
