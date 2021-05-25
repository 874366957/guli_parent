package com.atguigu.eduservice.banner.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.banner.entity.CrmBanner;
import com.atguigu.eduservice.banner.service.BannerAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-05-11
 */
@RestController
@CrossOrigin
@RequestMapping("/eduservice/adminbanner")
public class BannerAdminController {
    @Autowired
    private BannerAdminService bannerAdminService;
    @GetMapping("pageBanner/{page}/{limit}")
    public R pageBanner(@PathVariable long page, @PathVariable long limit){
        Map bannerMap=bannerAdminService.pageView(page,limit);
        return R.ok().data(bannerMap);
    }

    @PostMapping("addBanner")
    public R addBanner(@RequestBody CrmBanner crmBanner){
        bannerAdminService.save(crmBanner);
        return R.ok();
    }

    @PutMapping("update")
    @CacheEvict(value="banner",allEntries = true)
    public R updateBanner(@RequestBody CrmBanner crmBanner){
        bannerAdminService.updateById(crmBanner);
        return R.ok();
    }

    @DeleteMapping("delete/{id}")
    @CacheEvict(value="banner",allEntries = true)
    public R deleteBanner(@PathVariable String id){
        bannerAdminService.removeById(id);
        return R.ok();
    }

    @GetMapping("getBanner/{id}")
    public R getTeacher(@PathVariable String id) {
        CrmBanner crmBanner = bannerAdminService.getById(id);
        return R.ok().data("item", crmBanner);
    }
}

