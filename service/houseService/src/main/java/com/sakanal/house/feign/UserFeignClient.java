package com.sakanal.house.feign;

import com.sakanal.base.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Service
@FeignClient(value = "userService")
public interface UserFeignClient {
    @RequestMapping("/user/userbaseinfo/info/{id}")
    public R info(@PathVariable("id") Long id);
}
