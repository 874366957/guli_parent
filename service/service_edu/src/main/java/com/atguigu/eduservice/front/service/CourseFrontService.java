package com.atguigu.eduservice.front.service;

import com.atguigu.eduservice.base.entity.EduCourse;
import com.atguigu.eduservice.front.entity.CourseFrontVo;
import com.atguigu.eduservice.front.entity.CourseWebVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

public interface CourseFrontService extends IService<EduCourse> {
    Map<String, Object> getCourseFrontList(Page<EduCourse> pageCourse, CourseFrontVo courseFrontVo);

    CourseWebVo getBaseCourseInfo(String courseId);
}
