package com.atguigu.eduservice.front.service;

import com.atguigu.eduservice.front.entity.EduComment;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 评论 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-05-24
 */
public interface EduCommentService extends IService<EduComment> {

    Map<String, Object> getIndex(Page<EduComment> pageParam,String courseId);
}
