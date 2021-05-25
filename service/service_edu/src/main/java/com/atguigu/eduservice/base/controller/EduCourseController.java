package com.atguigu.eduservice.base.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.base.entity.EduCourse;
import com.atguigu.eduservice.base.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.base.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.base.entity.vo.CourseQuery;
import com.atguigu.eduservice.base.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-04-14
 */
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
public class EduCourseController {
    @Autowired
    private EduCourseService eduCourseService;

    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        String courseId = eduCourseService.saveCourseInfo(courseInfoVo);
        return R.ok().data("courseId", courseId);
    }

    @GetMapping("getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable String courseId) {
        CourseInfoVo courseInfoVo = eduCourseService.getCourseInfo(courseId);
        return R.ok().data("CourseInfoVo", courseInfoVo);
    }

    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        eduCourseService.updateCourseInfo(courseInfoVo);
        return R.ok();
    }

    @GetMapping("getPublishCourseInfo/{id}")
    public R getPublishCourseInfo(@PathVariable String id) {
        CoursePublishVo coursePublishVo = eduCourseService.publishCourseInfo(id);
        return R.ok().data("courseInfo", coursePublishVo);
    }

    @PostMapping("publishCourse/{id}")
    public R publishCourse(@PathVariable String id) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus(1);
        eduCourseService.updateById(eduCourse);
        return R.ok();
    }

    @GetMapping("pageCourse/{current}/{limit}")
    public R pageListCourse(@PathVariable long current,
                            @PathVariable long limit) {
        Map courseMap = eduCourseService.pageCourse(current, limit);

        return R.ok().data(courseMap);
    }

    @PostMapping("pageCourseCondition/{current}/{limit}")
    public R pageCourseCondition(@PathVariable long current, @PathVariable long limit, @RequestBody(required = false) CourseQuery courseQuery) {

        //创建配置对象
        Map pageCourseConditionMap = eduCourseService.pageCourseCondition(current, limit, courseQuery);

        return R.ok().data(pageCourseConditionMap);

    }

    @DeleteMapping("{courseId}")
    public R deleteCourse(@PathVariable String courseId) {
        eduCourseService.removeCourse(courseId);
        return R.ok();
    }
}

