package com.atguigu.eduservice.order.service.impl;

import com.atguigu.eduservice.front.entity.CourseWebVo;
import com.atguigu.eduservice.front.service.CourseFrontService;
import com.atguigu.eduservice.order.entity.TOrder;
import com.atguigu.eduservice.order.mapper.TOrderMapper;
import com.atguigu.eduservice.order.service.TOrderService;
import com.atguigu.eduservice.order.utils.OrderNoUtil;
import com.atguigu.eduservice.ucenter.entity.UcenterMember;
import com.atguigu.eduservice.ucenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

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

    @Autowired
    private TOrderService orderService;
    @Autowired
    private UcenterMemberService memberService;
    @Autowired
    private CourseFrontService courseService;
    @Override
    public String createOrder(String courseId, String memberIdByJwtToken) {
        UcenterMember member=memberService.getById(memberIdByJwtToken);
        CourseWebVo courseWebVo=courseService.getBaseCourseInfo(courseId);
        TOrder order=new TOrder();
        order.setOrderNo(OrderNoUtil.getOrderNo());
        order.setCourseId(courseId);
        order.setCourseTitle(courseWebVo.getTitle());
        order.setCourseCover(courseWebVo.getAvatar());
        order.setTeacherName(courseWebVo.getTeacherName());
        order.setTotalFee(courseWebVo.getPrice());
        order.setMemberId(memberIdByJwtToken);
        order.setMobile(member.getMobile());
        order.setNickname(member.getNickname());
        order.setStatus(0);
        order.setPayType(1);
        baseMapper.insert(order);
        System.out.println(order.getOrderNo());
        return order.getOrderNo();
    }

    @Override
    public TOrder getOrderInfo(String orderId) {
        QueryWrapper<TOrder> wrapper=new QueryWrapper<>();
        wrapper.eq("order_no",orderId);
        TOrder order = orderService.getOne(wrapper);
        return order;
    }
}
