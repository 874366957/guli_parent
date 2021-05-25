package com.atguigu.eduservice.front.service;

import com.atguigu.eduservice.base.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

public interface TeacherFrontService extends IService<EduTeacher> {
    Map<String,Object> getFrontList(Page<EduTeacher> pageTeacher);
}
