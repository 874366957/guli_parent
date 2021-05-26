package com.atguigu.eduservice.order.controller;


import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.eduservice.order.entity.TOrder;
import com.atguigu.eduservice.order.service.TOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-05-25
 */
@RestController
@CrossOrigin
@RequestMapping("/eduservice/order")
public class TOrderController {
    @Autowired
    private TOrderService orderService;

    @GetMapping("create/{courseId}")
    public R saveOrder(@PathVariable String courseId, HttpServletRequest request){
        if(request==null){
            return R.error().code(28004);
        }
        String orderNo=orderService.createOrder(courseId, JwtUtils.getMemberIdByJwtToken(request));
        return R.ok().data("orderId",orderNo);
    }

    @GetMapping("getOrderInfo/{orderId}")
    public R getOrderInfo(@PathVariable String orderId){
        TOrder orderInfo = orderService.getOrderInfo(orderId);
        return R.ok().data("item",orderInfo);
    }

}

