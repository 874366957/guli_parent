package com.atguigu.vod.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;

public class InitVodClient {
    public static DefaultAcsClient initVodClient(String accessId, String accessSecret) throws ClientException {
        String regionId = "cn-shanghai";
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessId, accessSecret);
        DefaultAcsClient client = new DefaultAcsClient(profile);
        return client;
    }
}
