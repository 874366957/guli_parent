package com.atguigu.eduservice.order.controller;


import com.alibaba.fastjson.serializer.BigDecimalCodec;
import com.atguigu.commonutils.R;
import com.atguigu.eduservice.order.service.TOrderService;
import com.atguigu.eduservice.order.service.TPayLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-05-25
 */
@RestController
@CrossOrigin
@RequestMapping("/eduservice/paylog")
public class TPayLogController {
    @Autowired
    private TPayLogService payLogService;

    @GetMapping("createNative/{orderNo}")
    public R getOrderInfo(@PathVariable String orderNo){
        Map map=payLogService.getOrder(orderNo);
        System.out.println(map);
        return R.ok().data(map);
    }

    @GetMapping("queryPayStatus/{orderNo}")
    public R queryPayStatus(@PathVariable String orderNo){
        Map<String,String> map=payLogService.getOrderStatus(orderNo);
        System.out.println(map);
        if(map==null){
            return R.error().message("支付出错");
        }
        if(map.get("trade_state").equals("SUCCESS")){
            payLogService.updatePayStatus(map);
            return R.ok().message("支付成功");
        }
        return R.ok().code(25000).message("支付中");
    }
}

