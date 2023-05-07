package com.sakanal.promotion.controller;

import com.alibaba.fastjson.TypeReference;
import com.sakanal.base.constant.ShowStateConstant;
import com.sakanal.base.exception.ErrorCodeEnum;
import com.sakanal.base.utils.PageUtils;
import com.sakanal.base.utils.R;
import com.sakanal.promotion.feign.AliPayFeignClient;
import com.sakanal.promotion.service.HousePromotionOrderService;
import com.sakanal.service.dto.PayAsyncDTO;
import com.sakanal.service.dto.PayDTO;
import com.sakanal.service.dto.PromotionOrderDTO;
import com.sakanal.service.entity.promotion.HousePromotionOrderEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


/**
 * 推广活动订单表
 *
 * @author sakanal
 * @email 1104820805@qq.com
 * @date 2023-04-16 10:41:39
 */
@RestController
@RequestMapping("promotion/housepromotionorder")
public class HousePromotionOrderController {
    @Resource
    private HousePromotionOrderService housePromotionOrderService;
    @Resource
    private AliPayFeignClient aliPayFeignClient;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("promotion:housepromotionorder:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = housePromotionOrderService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("promotion:housepromotionorder:info")
    public R info(@PathVariable("id") Long id){
		HousePromotionOrderEntity housePromotionOrder = housePromotionOrderService.getById(id);

        return R.ok().put("housePromotionOrder", housePromotionOrder);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("promotion:housepromotionorder:save")
    public R save(@RequestBody HousePromotionOrderEntity housePromotionOrder){
		housePromotionOrderService.save(housePromotionOrder);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("promotion:housepromotionorder:update")
    public R update(@RequestBody HousePromotionOrderEntity housePromotionOrder){
		housePromotionOrderService.updateById(housePromotionOrder);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("promotion:housepromotionorder:delete")
    public R delete(@RequestBody Long[] ids){
		housePromotionOrderService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }
    @DeleteMapping("/userDelete/{OrderId}")
    public R userDelete(@PathVariable Long OrderId){
        HousePromotionOrderEntity housePromotionOrderEntity = new HousePromotionOrderEntity();
        housePromotionOrderEntity.setId(OrderId);
        housePromotionOrderEntity.setShowState(ShowStateConstant.HIDE_STATUS);
        if (housePromotionOrderService.updateById(housePromotionOrderEntity)){
            return R.ok();
        }else {
            return R.error(ErrorCodeEnum.ORDER_DELETE_FAIL_EXCEPTION.getCode(), ErrorCodeEnum.ORDER_DELETE_FAIL_EXCEPTION.getMsg());
        }
    }

    @PostMapping("/createOrder")
    public R createOrder(@RequestBody @Valid PromotionOrderDTO promotionOrderDTO){
        PayDTO order = housePromotionOrderService.createOrder(promotionOrderDTO);
        if (order!=null){
            R r = aliPayFeignClient.simplePay(order);
            String pay = r.getData("pay", new TypeReference<String>() {
            });
            return R.ok().put("pay",pay);
        }
        return R.error();
    }

    @PostMapping("/toPay/{orderId}")
    public R toPay(@PathVariable Long orderId){
        PayDTO order = housePromotionOrderService.toPay(orderId);
        if (order!=null){
            R r = aliPayFeignClient.simplePay(order);
            String pay = r.getData("pay", new TypeReference<String>() {
            });
            return R.ok().put("pay",pay);
        }
        return R.error();
    }

    @PutMapping("/handlePayResult")
    public R handlePayResult(@RequestBody PayAsyncDTO payAsyncDTO){
        if (housePromotionOrderService.handlePayResult(payAsyncDTO)){
            return R.ok();
        }else {
            return R.error();
        }
    }

}
