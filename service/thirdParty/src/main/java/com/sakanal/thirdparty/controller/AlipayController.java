package com.sakanal.thirdparty.controller;

import com.alibaba.fastjson.TypeReference;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.sakanal.base.utils.R;
import com.sakanal.service.dto.PayAsyncDTO;
import com.sakanal.service.dto.PayDTO;
import com.sakanal.thirdparty.feign.PromotionOrderFeignClient;
import com.sakanal.thirdparty.template.AlipayTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/thirdParty/AliPay")
public class AlipayController {
    @Resource
    private AlipayTemplate alipayTemplate;
    @Resource
    private PromotionOrderFeignClient promotionOrderFeignClient;
    @PostMapping("/simplePay")
    public R simplePay(@RequestBody PayDTO payDTO){
        try {
            String pay = alipayTemplate.pay(payDTO);
            return R.ok().put("pay",pay);
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return R.error();
        }
    }


    @PostMapping("/notifyOrder")
    public String handleAlipayed(PayAsyncDTO asyncVo, HttpServletRequest request) throws AlipayApiException, UnsupportedEncodingException {
        // 只要收到支付宝的异步通知，返回 success 支付宝便不再通知
        // 获取支付宝POST过来反馈信息
        // 需要验签
        Map<String, String> params = new HashMap<>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (String name : requestParams.keySet()) {
            String[] values = requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            // valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        boolean signVerified = AlipaySignature.rsaCheckV1(params, alipayTemplate.getAlipay_public_key(),
                alipayTemplate.getCharset(), alipayTemplate.getSign_type()); //调用SDK验证签名

        if (signVerified) {
            System.out.println("签名验证成功...");
            //去修改订单状态
            Integer code = promotionOrderFeignClient.handlePayResult(asyncVo).getData("code", new TypeReference<Integer>() {
            });
            if (code==0){
                return "success";
            }
        } else {
            System.out.println("签名验证失败...");
            return "error";
        }
        return "error";
    }


}
