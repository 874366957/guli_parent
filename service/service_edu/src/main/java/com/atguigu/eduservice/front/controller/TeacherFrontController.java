package com.atguigu.eduservice.front.controller;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.base.entity.EduCourse;
import com.atguigu.eduservice.base.entity.EduTeacher;
import com.atguigu.eduservice.front.service.TeacherFrontService;
import com.atguigu.eduservice.base.service.EduCourseService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/eduservice/teacherfront")
public class TeacherFrontController {
    @Autowired
    private TeacherFrontService teacherService;
    @Autowired
    private EduCourseService courseService;

    @GetMapping("getTeacherFront/{page}/{limit}")
    public R getTeacherFrontList(@PathVariable long page,@PathVariable long limit){
        Page<EduTeacher> pageTeacher=new Page<>(page,limit);
        Map<String, Object> map = teacherService.getFrontList(pageTeacher);
        return R.ok().data(map);
    }

    @GetMapping("getTeacherFrontInfo/{id}")
    public R getTeacherFrontInfo(@PathVariable String id){
        EduTeacher teacher = teacherService.getById(id);
        List<EduCourse> list = courseService.getByTeacherId(id);
        return R.ok().data("teacher",teacher).data("courseList",list);
    }
}
