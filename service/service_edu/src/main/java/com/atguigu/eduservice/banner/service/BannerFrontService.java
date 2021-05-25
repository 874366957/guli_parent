package com.atguigu.eduservice.banner.service;

import com.atguigu.eduservice.banner.entity.CrmBanner;
import com.atguigu.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-05-11
 */
public interface BannerFrontService extends IService<CrmBanner> {

    List selectAll();

    List getFrontCourseIndex();

    List<EduTeacher> getFrontTeacherIndex();
    List<CrmBanner> selectAllBanner();
}
