package com.sakanal.thirdparty.controller;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import com.sakanal.base.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/thirdParty/OSS")
public class OssController {

    @Resource
    OSSClient ossClient;

    @Value("${spring.cloud.alicloud.access-key}")
    private String accessKey;

    @Value("${spring.cloud.alicloud.oss.bucket}")
    private String bucket;

    @Value("${spring.cloud.alicloud.oss.endpoint}")
    private String endpoint;


    @RequestMapping("/upload/{target}")
    public R upload(@PathVariable("target") String target) {
        String host = "https://" + bucket + "." + endpoint;
        String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date()).replace("-","/");
        String dir = target + "/" + format + "/"; // 用户上传文件时指定的前缀。

        Map<String, String> respMap = null;
        try {
            long expireTime = 30;
            long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
            Date expiration = new Date(expireEndTime);
            PolicyConditions policyConditions = new PolicyConditions();
            policyConditions.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
            policyConditions.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);

            String postPolicy = ossClient.generatePostPolicy(expiration, policyConditions);
            byte[] binaryData = postPolicy.getBytes(StandardCharsets.UTF_8);
            String encodedPolicy = BinaryUtil.toBase64String(binaryData);
            String postSignature = ossClient.calculatePostSignature(postPolicy);

            respMap = new LinkedHashMap<>();
            respMap.put("accessKey", accessKey);
            respMap.put("policy", encodedPolicy);//
            respMap.put("signature", postSignature);//
            respMap.put("dir", dir);//
            respMap.put("host", host);//
            respMap.put("expire", String.valueOf(expireEndTime / 1000));

        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return R.ok().put("data", respMap);
    }

    @DeleteMapping(value = "/remove")
    public R remove(@RequestBody Map<String, String> fileList){
        String host = "https://" + bucket + "." + endpoint + "/";
        String file = fileList.get("fileUrl").replace(host, "");
        try {
            // 删除文件或目录。如果要删除目录，目录必须为空。
            ossClient.deleteObject(bucket,file);
        } catch (OSSException oe) {
            log.error("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            log.error("Error Message:" + oe.getErrorMessage());
            log.error("Error Code:" + oe.getErrorCode());
            log.error("Request ID:" + oe.getRequestId());
            log.error("Host ID:" + oe.getHostId());
            return R.error("图片删除失败");
        } catch (ClientException ce) {
            log.error("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            log.error("Error Message:" + ce.getMessage());
            return R.error("图片删除失败");
        }
        return R.ok();
    }
}
