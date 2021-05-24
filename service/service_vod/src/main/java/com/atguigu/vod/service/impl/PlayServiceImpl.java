package com.atguigu.vod.service.impl;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.atguigu.vod.service.PlayService;
import com.atguigu.vod.utils.ConstantVideoProperties;
import com.atguigu.vod.utils.InitVodClient;
import org.springframework.stereotype.Service;

@Service
public class PlayServiceImpl implements PlayService {
    @Override
    public String getAuth(String id) {
        try {
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantVideoProperties.ACCESS_KEY_ID, ConstantVideoProperties.ACCESS_KET_SECRET);
            GetVideoPlayAuthRequest request=new GetVideoPlayAuthRequest();
            request.setVideoId(id);
            GetVideoPlayAuthResponse response=client.getAcsResponse(request);
            String auth = response.getPlayAuth();
            return auth;
        }catch (Exception e){
            throw new GuliException(20001,"WRONG!");
        }
    }
}
