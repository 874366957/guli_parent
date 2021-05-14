package com.atguigu.msmservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.msmservice.service.MsmService;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20190711.SmsClient;
import com.tencentcloudapi.sms.v20190711.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;
import com.tencentcloudapi.sms.v20190711.models.SendStatus;
import org.junit.Test;
import org.springframework.stereotype.Service;

@Service
public class MsmServiceImpl implements MsmService {
    public boolean sendMessage(String phoneNumber,String code){
        try {
            Credential cred = new Credential("AKID97pGPIK6bolRqypPijTw83g3A0ZsvhCQ", "ye3B3dwykGdmfHgpiKfjW3JHyPxEExPa");
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setReqMethod("POST");
            httpProfile.setConnTimeout(60);
            httpProfile.setEndpoint("sms.tencentcloudapi.com");
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setSignMethod("HmacSHA256");
            clientProfile.setHttpProfile(httpProfile);
            SmsClient client = new SmsClient(cred, "ap-guangzhou",clientProfile);
            SendSmsRequest req = new SendSmsRequest();
            String appid = "1400519748";
            req.setSmsSdkAppid(appid);
            String sign = "GaryTemplate";
            req.setSign(sign);
            String templateID = "957687";
            req.setTemplateID(templateID);
            String finalPhoneNumber="+86"+phoneNumber;
            String[] phoneNumbers = {finalPhoneNumber};
            req.setPhoneNumberSet(phoneNumbers);
            /* 模板参数: 若无模板参数，则设置为空*/
            String[] templateParams = {code};
            req.setTemplateParamSet(templateParams);
            SendSmsResponse res = client.SendSms(req);
            // 输出 JSON 格式的字符串回包
            //System.out.println(SendSmsResponse.toJsonString(res));
            JSONObject js=JSONObject.parseObject(SendSmsResponse.toJsonString(res));
            // 可以取出单个值，您可以通过官网接口文档或跳转到 response 对象的定义处查看返回字段的定义
            String str=(js.getJSONArray("SendStatusSet").getJSONObject(0).get("Code")).toString();
            System.out.println(str);
            if(str.equals("Ok")){
                return true;
            }else {
                return false;
            }
        } catch (TencentCloudSDKException e) {
            e.printStackTrace();
            return false;
        }
    }
}
