package com.sakanal.promotion.feign;

import com.sakanal.base.utils.R;
import com.sakanal.service.dto.PromotionNumberDTO;
import com.sakanal.service.dto.PublishInfoListDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Service
@FeignClient(value = "houseService")
public interface HouseFeignClient {
    @PutMapping("/house/houseInfo/countPublish/{userId}")
    public R countPublish(@PathVariable Long userId);

    @PutMapping("/house/housepromotion/addPromotionNumber")
    public R addPromotion(@RequestBody PromotionNumberDTO promotionNumberDTO);
}
