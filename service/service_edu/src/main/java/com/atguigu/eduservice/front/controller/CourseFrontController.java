package com.atguigu.eduservice.front.controller;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.atguigu.eduservice.front.entity.CourseFrontVo;
import com.atguigu.eduservice.front.entity.CourseWebVo;
import com.atguigu.eduservice.front.service.CourseFrontService;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/eduservice/coursefront")
public class CourseFrontController {
    @Autowired
    private CourseFrontService courseService;
    @Autowired
    private EduChapterService chapterService;

    @PostMapping("getFrontCoourseList/{page}/{limit}")
    public R getFrontCourseList(@PathVariable long page, @PathVariable long limit, @RequestBody(required = false) CourseFrontVo courseFrontVo){
        Page<EduCourse> pageCourse=new Page<>(page,limit);
        Map<String,Object> map=courseService.getCourseFrontList(pageCourse,courseFrontVo);
        return R.ok().data(map);
    }

    @GetMapping("getFrontCourseInfo/{courseId}")
    public R getFrontCourseInfo(@PathVariable String courseId){
        CourseWebVo courseWebVo=courseService.getBaseCourseInfo(courseId);
        List<ChapterVo> courseList = chapterService.getChapterVideoByCourseId(courseId);
        return R.ok().data("courseWebVo",courseWebVo).data("courseList",courseList);
    }
}
