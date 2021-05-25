package com.atguigu.eduservice.banner.service.impl;

import com.atguigu.eduservice.banner.entity.CrmBanner;
import com.atguigu.eduservice.mapper.CrmBannerMapper;
import com.atguigu.eduservice.banner.service.BannerAdminService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-05-11
 */
@Service
public class BannerAdminServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements BannerAdminService {
    @Autowired
    private BannerAdminService bannerAdminService;
    @Override
    public Map pageView(long page, long limit) {
        Page<CrmBanner> pageBanner=new Page<>(page,limit);
        bannerAdminService.page(pageBanner,null);
        long total = pageBanner.getTotal();
        List<CrmBanner> records = pageBanner.getRecords();

        Map map = new HashMap<>();
        map.put("total", total);
        map.put("rows", records);

        return map;
    }
}
