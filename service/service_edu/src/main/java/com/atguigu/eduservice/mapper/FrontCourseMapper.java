package com.atguigu.eduservice.mapper;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.front.entity.CourseWebVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface FrontCourseMapper extends BaseMapper<EduCourse>{
    CourseWebVo getBaseCourseInfo(String courseId);
}
