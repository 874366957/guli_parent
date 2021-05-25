package com.atguigu.eduservice.banner.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.banner.entity.CrmBanner;
import com.atguigu.eduservice.banner.service.BannerFrontService;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
@RequestMapping("/eduservice/frontbanner")
public class BannerFrontController {
    @Autowired
    private BannerFrontService bannerFrontService;
    @GetMapping("getAllBanner")
    public R getAllBanner(){
        List list=bannerFrontService.selectAll();
        return R.ok().data("list",list);
    }

    @GetMapping("index")
    public R getIndex(){
        List<EduCourse> listCourse=bannerFrontService.getFrontCourseIndex();
        List<EduTeacher> listTeacher=bannerFrontService.getFrontTeacherIndex();
        return R.ok().data("courseList",listCourse).data("teacherList",listTeacher);
    }

    @GetMapping("getTwoBanner")
    public R getTwoBanner(){
        List<CrmBanner> list=bannerFrontService.selectAllBanner();
        return R.ok().data("bannerList",list);
    }

}

