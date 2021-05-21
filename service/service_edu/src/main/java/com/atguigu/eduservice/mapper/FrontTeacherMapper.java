package com.atguigu.eduservice.mapper;

import com.atguigu.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface FrontTeacherMapper extends BaseMapper<EduTeacher> {
}
