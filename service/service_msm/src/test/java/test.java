import com.alibaba.fastjson.JSONObject;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
//导入可选配置类
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
// 导入 SMS 模块的 client
import com.tencentcloudapi.sms.v20190711.SmsClient;
// 导入要请求接口对应的 request response 类
import com.tencentcloudapi.sms.v20190711.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;
/**
 * Tencent Cloud Sms Sendsms
 * https://cloud.tencent.com/document/product/382/38778
 *
 */
public class test
{
    public static void main(String[] args)
    {
            sendMessage("+8617798561806");
    }
    public static void sendMessage(String phoneNumber){
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
            String[] phoneNumbers = {phoneNumber};
            req.setPhoneNumberSet(phoneNumbers);
            /* 模板参数: 若无模板参数，则设置为空*/
            String code=String.valueOf((int)((Math.random()*9+1)*100000));
            String[] templateParams = {code};
            req.setTemplateParamSet(templateParams);
            SendSmsResponse res = client.SendSms(req);
            // 输出 JSON 格式的字符串回包
            //System.out.println(SendSmsResponse.toJsonString(res));
            JSONObject js=JSONObject.parseObject(SendSmsResponse.toJsonString(res));
            // 可以取出单个值，您可以通过官网接口文档或跳转到 response 对象的定义处查看返回字段的定义
            String str=(js.getJSONArray("SendStatusSet").getJSONObject(0).get("Code")).toString();
            System.out.println(str);
        } catch (TencentCloudSDKException e) {
            e.printStackTrace();
        }
    }
}

