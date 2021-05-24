package com.atguigu.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.atguigu.commonutils.R;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.atguigu.vod.service.PlayService;
import com.atguigu.vod.utils.InitVodClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.atguigu.vod.utils.ConstantVideoProperties;

@RestController
@CrossOrigin
@RequestMapping("/eduvod/video")
public class PlayerController {
    @Autowired
    private PlayService playService;
    @GetMapping("getPlayAuth/{id}")
    public R getPlayAuth(@PathVariable String id){
        try {
            String auth=playService.getAuth(id);
            return R.ok().data("playAuth",auth);
        }catch (Exception e){
            e.printStackTrace();
            throw new GuliException(20001,"获取视频失败");
        }
    }
}
