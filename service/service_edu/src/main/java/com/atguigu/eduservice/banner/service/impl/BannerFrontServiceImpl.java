package com.atguigu.eduservice.banner.service.impl;

import com.atguigu.eduservice.banner.entity.CrmBanner;
import com.atguigu.eduservice.banner.mapper.CrmBannerMapper;
import com.atguigu.eduservice.banner.service.BannerFrontService;
import com.atguigu.eduservice.base.entity.EduCourse;
import com.atguigu.eduservice.base.entity.EduTeacher;
import com.atguigu.eduservice.base.service.EduCourseService;
import com.atguigu.eduservice.base.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-05-11
 */
@Service
public class BannerFrontServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements BannerFrontService {
    @Autowired
    private EduTeacherService eduTeacherService;
    @Autowired
    private EduCourseService eduCourseService;

    @Cacheable(value = "banner",key = "'selectIndexList'")
    @Override
    public List selectAll() {
        List<CrmBanner> banners = baseMapper.selectList(null);
        return banners;
    }

    @Override
    public List getFrontCourseIndex() {
        QueryWrapper<EduCourse> courseQueryWrapper=new QueryWrapper<>();
        courseQueryWrapper.orderByDesc("id");
        courseQueryWrapper.last("limit 8");
        List<EduCourse> list=eduCourseService.list(courseQueryWrapper);
        return list;
    }

    @Override
    public List<EduTeacher> getFrontTeacherIndex() {
        QueryWrapper<EduTeacher> teacherQueryWrapper=new QueryWrapper<>();
        teacherQueryWrapper.orderByDesc("id");
        teacherQueryWrapper.last("limit 4");
        List<EduTeacher> list=eduTeacherService.list(teacherQueryWrapper);
        return list;
    }
    @Override
    public List<CrmBanner> selectAllBanner(){
        QueryWrapper<CrmBanner> wrapper=new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 2");
        List<CrmBanner> list=baseMapper.selectList(wrapper);

        return list;
    }
}
