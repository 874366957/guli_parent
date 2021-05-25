package com.atguigu.eduservice.banner.service;

import com.atguigu.eduservice.banner.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-05-11
 */
public interface BannerAdminService extends IService<CrmBanner> {

    Map pageView(long page, long limit);
}
