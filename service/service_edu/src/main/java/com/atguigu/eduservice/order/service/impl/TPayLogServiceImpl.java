package com.atguigu.eduservice.order.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.commonutils.MD5;
import com.atguigu.eduservice.order.entity.TOrder;
import com.atguigu.eduservice.order.entity.TPayLog;
import com.atguigu.eduservice.order.mapper.TPayLogMapper;
import com.atguigu.eduservice.order.service.TOrderService;
import com.atguigu.eduservice.order.service.TPayLogService;
import com.atguigu.eduservice.order.utils.OrderHttpClient;
import com.atguigu.eduservice.order.utils.OrderUtils;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.wxpay.sdk.WXPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-05-25
 */
@Service
public class TPayLogServiceImpl extends ServiceImpl<TPayLogMapper, TPayLog> implements TPayLogService {

    @Autowired
    private TOrderService orderService;
    @Override
    public Map getOrder(String orderNo) {
        try {
            QueryWrapper<TOrder> wrapper=new QueryWrapper<>();
            wrapper.eq("order_no",orderNo);
            TOrder order = orderService.getOne(wrapper);
            Map map=new HashMap();
            map.put("appid", OrderUtils.APP_ID);
            map.put("mch_id",OrderUtils.PARTNER);
            map.put("nonce_str", WXPayUtil.generateNonceStr());
            map.put("body",order.getCourseTitle());
            map.put("out_trade_no",orderNo);
            map.put("total_fee",order.getTotalFee().multiply(new BigDecimal("100")).longValue()+"");
            map.put("spbill_create_ip","127.0.0.1");
            map.put("notify_url","http://guli.shop/api/order/weixinPay/weixinNotify\n");
            map.put("trade_type", "NATIVE");
            System.out.println(map);
            OrderHttpClient client=new OrderHttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
            client.setXmlParam(WXPayUtil.generateSignedXml(map,OrderUtils.PARTNER_KEY));
            client.setHttps(true);
            client.post();
            String content = client.getContent();
            Map<String,String> result=new HashMap<>();
            result=WXPayUtil.xmlToMap(content);
            Map paramOut=new HashMap();
            paramOut.put("out_trade_no",orderNo);
            paramOut.put("course_Id",order.getCourseId());
            paramOut.put("total_fee",order.getTotalFee());
            paramOut.put("result_code",result.get("result_code"));
            paramOut.put("code_url",result.get("code_url"));
            return paramOut;
        }catch (Exception e){
            throw new GuliException(20001,"生成二维码失败");
        }
    }

    @Override
    public Map<String, String> getOrderStatus(String orderNo) {
        try {
            String nonce=WXPayUtil.generateNonceStr();
            Map inParam = new HashMap();
            inParam.put("appid", OrderUtils.APP_ID);
            inParam.put("mch_id", OrderUtils.PARTNER);
            inParam.put("out_trade_no", orderNo);
            inParam.put("nonce_str", nonce);
            OrderHttpClient client = new OrderHttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
            client.setXmlParam(WXPayUtil.generateSignedXml(inParam, OrderUtils.PARTNER_KEY));
            client.setHttps(true);
            client.post();
            String content=client.getContent();
            Map<String,String> outParam=WXPayUtil.xmlToMap(content);
            return outParam;
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public void updatePayStatus(Map<String, String> inParam) {
        String orderNo=inParam.get("out_trade_no");
        QueryWrapper<TOrder> wrapper=new QueryWrapper<>();
        wrapper.eq("order_no",orderNo);
        TOrder order=orderService.getOne(wrapper);
        if(order.getStatus().intValue()==1){
            return;
        }
        order.setStatus(1);
        orderService.updateById(order);

        TPayLog payLog=new TPayLog();
        payLog.setOrderNo(orderNo);
        payLog.setPayTime(new Date());
        payLog.setPayType(1);
        payLog.setTotalFee(order.getTotalFee());
        payLog.setTradeState(inParam.get("trade_state"));
        payLog.setTransactionId(inParam.get("transaction_id"));
        payLog.setAttr(JSONObject.toJSONString(inParam));
        baseMapper.insert(payLog);
    }
}
