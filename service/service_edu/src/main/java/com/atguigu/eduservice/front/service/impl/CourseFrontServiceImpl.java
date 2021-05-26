package com.atguigu.eduservice.front.service.impl;

import com.atguigu.eduservice.base.entity.EduCourse;
import com.atguigu.eduservice.front.entity.CourseFrontVo;
import com.atguigu.eduservice.front.entity.CourseWebVo;
import com.atguigu.eduservice.front.service.CourseFrontService;
import com.atguigu.eduservice.front.mapper.FrontCourseMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CourseFrontServiceImpl extends ServiceImpl<FrontCourseMapper, EduCourse> implements CourseFrontService {
    @Autowired
    private FrontCourseMapper courseMapper;
    @Override
    public Map<String, Object> getCourseFrontList(Page<EduCourse> pageParam, CourseFrontVo courseFrontVo) {
        QueryWrapper<EduCourse> wrapper=new QueryWrapper<>();
        if(!StringUtils.isEmpty(courseFrontVo.getSubjectParentId())){
            wrapper.eq("subject_parent_id",courseFrontVo.getSubjectParentId());
        }
        if(!StringUtils.isEmpty(courseFrontVo.getSubjectId())){
            wrapper.eq("subject_id",courseFrontVo.getSubjectId());
        }
        if(!StringUtils.isEmpty(courseFrontVo.getBuyCountSort())){
            wrapper.orderByDesc("buy_count");
        }
        if(!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort())){
            wrapper.orderByDesc("gmt_create");
        }
        if(!StringUtils.isEmpty(courseFrontVo.getPriceSort())){
            wrapper.orderByDesc("price");
        }
        baseMapper.selectPage(pageParam,wrapper);

        List<EduCourse> records=pageParam.getRecords();
        long current=pageParam.getCurrent();
        long pages=pageParam.getPages();
        long size=pageParam.getSize();
        long total=pageParam.getTotal();
        boolean hasNext=pageParam.hasNext();
        boolean hasPrevious=pageParam.hasPrevious();

        HashMap<String,Object> map=new HashMap<>();
        map.put("items",records);
        map.put("current",current);
        map.put("pages",pages);
        map.put("size",size);

        map.put("total",total);
        map.put("hasNext",hasNext);
        map.put("hasPrevious",hasPrevious);
        return map;
    }

    @Override
    public CourseWebVo getBaseCourseInfo(String courseId) {
        return baseMapper.getBaseCourseInfo(courseId);
    }
}
