package com.sakanal.house.feign;

import com.sakanal.base.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Service
@FeignClient(value = "userService")
public interface UserFeignClient {
    @RequestMapping("/user/userbaseinfo/info/{id}")
    public R info(@PathVariable("id") Long id);
}
