package com.atguigu.eduservice.base.service;

import com.atguigu.eduservice.base.entity.EduTeacher;
import com.atguigu.eduservice.base.entity.vo.TeacherQuery;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-04-02
 */
public interface EduTeacherService extends IService<EduTeacher> {
    Map teacherListMap(long current, long limit);

    Map teacherConditionListMap(long current, long limit, TeacherQuery teacherQuery);

}
