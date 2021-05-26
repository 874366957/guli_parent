package com.atguigu.eduservice.order.service;

import com.atguigu.eduservice.order.entity.TPayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;
import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-05-25
 */
public interface TPayLogService extends IService<TPayLog> {

    Map getOrder(String orderNo);

    Map<String, String> getOrderStatus(String orderNo);

    void updatePayStatus(Map<String, String> map);
}
