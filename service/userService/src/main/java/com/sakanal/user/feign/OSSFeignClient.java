package com.sakanal.user.feign;

import com.sakanal.base.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Service
@FeignClient(value = "thirdParty",contextId = "OSS")
public interface OSSFeignClient {

    @DeleteMapping(value = "/thirdParty/OSS/remove")
    public R remove(@RequestBody Map<String, String> fileList);
}
