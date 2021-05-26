package com.atguigu.eduservice.order.service;

import com.atguigu.eduservice.order.entity.TOrder;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-05-25
 */
public interface TOrderService extends IService<TOrder> {

    String createOrder(String courseId, String memberIdByJwtToken);

    TOrder getOrderInfo(String orderId);

}
