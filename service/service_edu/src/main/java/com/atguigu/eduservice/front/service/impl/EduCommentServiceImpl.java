package com.atguigu.eduservice.front.service.impl;

import com.atguigu.eduservice.front.entity.EduComment;
import com.atguigu.eduservice.mapper.EduCommentMapper;
import com.atguigu.eduservice.front.service.EduCommentService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-05-24
 */
@Service
public class EduCommentServiceImpl extends ServiceImpl<EduCommentMapper, EduComment> implements EduCommentService {

    @Override
    public Map<String, Object> getIndex(Page<EduComment> pageParam, String courseId) {
        QueryWrapper<EduComment> wrapper=new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        wrapper.orderByDesc("gmt_create");
        baseMapper.selectPage(pageParam,wrapper);
        List<EduComment> list=pageParam.getRecords();

        Map<String,Object> map=new HashMap<>();
        map.put("items", list);
        map.put("current", pageParam.getCurrent());
        map.put("pages", pageParam.getPages());
        map.put("size", pageParam.getSize());
        map.put("total", pageParam.getTotal());
        map.put("hasNext", pageParam.hasNext());
        map.put("hasPrevious", pageParam.hasPrevious());
        return map;
    }
}
