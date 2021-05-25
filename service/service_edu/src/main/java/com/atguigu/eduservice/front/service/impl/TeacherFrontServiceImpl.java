package com.atguigu.eduservice.front.service.impl;

import com.atguigu.eduservice.base.entity.EduTeacher;
import com.atguigu.eduservice.front.service.TeacherFrontService;
import com.atguigu.eduservice.mapper.FrontTeacherMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TeacherFrontServiceImpl extends ServiceImpl<FrontTeacherMapper, EduTeacher> implements TeacherFrontService {
    public Map<String,Object> getFrontList(Page<EduTeacher> pageTeacher) {
        QueryWrapper<EduTeacher> wrapper=new QueryWrapper<>();
        wrapper.orderByDesc("id");
        baseMapper.selectPage(pageTeacher, wrapper);
        List<EduTeacher> list=pageTeacher.getRecords();
        long current=pageTeacher.getCurrent();
        long total = pageTeacher.getTotal();
        long size = pageTeacher.getSize();
        long pages = pageTeacher.getPages();
        boolean hasNext = pageTeacher.hasNext();
        boolean hasPrevious = pageTeacher.hasPrevious();
        Map<String,Object> map=new HashMap<>();
        map.put("items",list);
        map.put("current",current);
        map.put("pages",pages);
        map.put("size",size);
        map.put("total",total);
        map.put("hasNext",hasNext);
        map.put("hasPrevious",hasPrevious);
        return map;
    }
}
