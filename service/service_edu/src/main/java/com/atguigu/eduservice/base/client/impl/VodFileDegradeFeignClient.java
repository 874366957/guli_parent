package com.atguigu.eduservice.base.client.impl;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.base.client.VodClient;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VodFileDegradeFeignClient implements VodClient {
    @Override
    public R removeAlyVideo(String id) {
        return R.error().message("wrong!");
    }

    @Override
    public R deleteBatch(List<String> videoIdList) {
        return R.error().message("WRONG!!!");
    }
}
