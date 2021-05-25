package com.atguigu.eduservice.base.service;

import com.atguigu.eduservice.base.entity.EduCourse;
import com.atguigu.eduservice.base.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.base.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.base.entity.vo.CourseQuery;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-04-14
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseInfo(CourseInfoVo courseInfoVo);

    CourseInfoVo getCourseInfo(String courseId);

    void updateCourseInfo(CourseInfoVo courseInfoVo);

    CoursePublishVo publishCourseInfo(String id);

    void removeCourse(String courseId);

    Map pageCourse(long current, long limit);

    Map pageCourseCondition(long current, long limit, CourseQuery courseQuery);

    List<EduCourse> getByTeacherId(String id);
}
