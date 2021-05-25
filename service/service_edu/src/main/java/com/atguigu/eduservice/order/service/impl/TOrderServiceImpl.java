package com.atguigu.eduservice.order.service.impl;

import com.atguigu.eduservice.order.entity.TOrder;
import com.atguigu.eduservice.mapper.TOrderMapper;
import com.atguigu.eduservice.order.service.TOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-05-25
 */
@Service
public class TOrderServiceImpl extends ServiceImpl<TOrderMapper, TOrder> implements TOrderService {

    @Override
    public void createOrder(String courseId, String memberIdByJwtToken) {

    }
}
