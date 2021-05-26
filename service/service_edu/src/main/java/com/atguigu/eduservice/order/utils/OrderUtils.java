package com.atguigu.eduservice.order.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class OrderUtils implements InitializingBean {
    @Value("${wx.pay.app_id}")
    private String appId;
    @Value("${wx.pay.partner}")
    private String partner;
    @Value("${wx.pay.partner_key}")
    private String partnerKey;
    @Value("${wx.pay.notify_url}")
    private String notifyUrl;

    public static String APP_ID;
    public static String PARTNER;
    public static String PARTNER_KEY;
    public static String NOTIFY_URL;

    @Override
    public void afterPropertiesSet() throws Exception {
        APP_ID=appId;
        PARTNER=partner;
        PARTNER_KEY=partnerKey;
        NOTIFY_URL=notifyUrl;
    }
}
