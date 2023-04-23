package com.sakanal.thirdparty.feign;

import com.sakanal.base.utils.R;
import com.sakanal.service.dto.PayAsyncDTO;
import com.sakanal.service.dto.PayDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@FeignClient(value = "promotionService")
public interface PromotionOrderFeignClient {
    @PutMapping("/promotion/housepromotionorder/handlePayResult")
    public R handlePayResult(@RequestBody PayAsyncDTO payAsyncDTO);
}
