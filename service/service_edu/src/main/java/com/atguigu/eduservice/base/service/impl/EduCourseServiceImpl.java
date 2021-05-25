package com.atguigu.eduservice.base.service.impl;

import com.atguigu.eduservice.base.entity.EduCourse;
import com.atguigu.eduservice.base.entity.EduCourseDescription;
import com.atguigu.eduservice.base.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.base.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.base.entity.vo.CourseQuery;
import com.atguigu.eduservice.mapper.EduCourseMapper;
import com.atguigu.eduservice.base.service.EduChapterService;
import com.atguigu.eduservice.base.service.EduCourseDescriptionService;
import com.atguigu.eduservice.base.service.EduCourseService;
import com.atguigu.eduservice.base.service.EduVideoService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-04-14
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseService eduCourseService;
    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;
    @Autowired
    private EduVideoService eduVideoService;
    @Autowired
    private EduChapterService eduChapterService;

    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int insert = baseMapper.insert(eduCourse);
        if (insert == 0) {
            throw new GuliException(20001, "插入错误");
        }
        String courseId = eduCourse.getId();
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setDescription(courseInfoVo.getDescription());
        eduCourseDescription.setId(courseId);
        eduCourseDescriptionService.save(eduCourseDescription);
        return courseId;
    }

    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        EduCourse eduCourse = baseMapper.selectById(courseId);
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse, courseInfoVo);
        EduCourseDescription eduCourseDescription = eduCourseDescriptionService.getById(courseId);
        courseInfoVo.setDescription(eduCourseDescription.getDescription());
        return courseInfoVo;
    }

    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int update = baseMapper.updateById(eduCourse);
        if (update == 0) {
            throw new GuliException(20001, "更新失败");
        }
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setId(courseInfoVo.getId());
        eduCourseDescription.setDescription(courseInfoVo.getDescription());
        eduCourseDescriptionService.updateById(eduCourseDescription);
    }

    @Override
    public CoursePublishVo publishCourseInfo(String id) {
        CoursePublishVo publishCourseInfo = baseMapper.getPublishCourseInfo(id);
        return publishCourseInfo;
    }

    @Override
    public void removeCourse(String courseId) {
        eduVideoService.removeByCourseId(courseId);
        eduChapterService.removeByCourseId(courseId);
        eduCourseDescriptionService.removeById(courseId);
        int flag = baseMapper.deleteById(courseId);
        if (flag == 0) {
            throw new GuliException(20001, "Exception");
        }
    }

    @Override
    public Map pageCourse(long current, long limit) {
        Page<EduCourse> pageCourse = new Page<>(current, limit);
        //底层封装，把所有对象封装到teacherService中
        eduCourseService.page(pageCourse, null);
        long total = pageCourse.getTotal();
        List<EduCourse> records = pageCourse.getRecords();

        Map map = new HashMap<>();
        map.put("total", total);
        map.put("rows", records);

        return map;
    }

    @Override
    public Map pageCourseCondition(long current, long limit, CourseQuery courseQuery) {
        Page<EduCourse> pageCourse = new Page<>(current, limit);
        //构建条件

        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        String title = courseQuery.getTitle();
        Integer status = courseQuery.getStatus();
        Integer lessonNum = courseQuery.getLessonNum();
        String beginTime = courseQuery.getBeginTime();
        String endTime = courseQuery.getEndTime();
        Integer viewCount = courseQuery.getViewCount();
        if (!StringUtils.isEmpty(title)) {
            wrapper.like("title", title);
        }
        if (!StringUtils.isEmpty(status)) {
            wrapper.eq("status", status);
        }
        if (!StringUtils.isEmpty(lessonNum)) {
            wrapper.eq("lesson_num", lessonNum);
        }
        if (!StringUtils.isEmpty(beginTime)) {
            wrapper.ge("gmt_create", beginTime);
        }
        if (!StringUtils.isEmpty(endTime)) {
            wrapper.le("gmt_create", endTime);
        }
        if (!StringUtils.isEmpty(viewCount)) {
            wrapper.eq("view_count", viewCount);
        }

        wrapper.orderByDesc("gmt_create");
        eduCourseService.page(pageCourse, wrapper);

        long total = pageCourse.getTotal();
        List<EduCourse> records = pageCourse.getRecords();

        Map map = new HashMap<>();
        map.put("total", total);
        map.put("rows", records);

        return map;
    }

    @Override
    public List<EduCourse> getByTeacherId(String id) {
        QueryWrapper<EduCourse> wrapper=new QueryWrapper<>();
        wrapper.eq("teacher_id",id);
        List<EduCourse> list = eduCourseService.list(wrapper);
        return list;
    }
}
