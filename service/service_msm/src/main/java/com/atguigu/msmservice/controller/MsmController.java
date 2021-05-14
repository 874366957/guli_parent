package com.atguigu.msmservice.controller;

import com.atguigu.commonutils.R;
import com.atguigu.msmservice.service.MsmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/edumsm/msm")
@CrossOrigin
public class MsmController {
    @Autowired
    private MsmService msmService;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @PostMapping("sendMessage/{number}")
    public R sendMsm(@PathVariable String number){
        String keyValue=redisTemplate.opsForValue().get(number);
        if(!StringUtils.isEmpty(keyValue)){
            return R.ok();
        }
        String code=String.valueOf((int)((Math.random()*9+1)*100000));
        boolean flag=msmService.sendMessage(number,code);
        if(flag){
            redisTemplate.opsForValue().set(number,code,5, TimeUnit.MINUTES);
            return R.ok();
        }
        else{
            return R.error().message("发送失败");
        }
    }
}
