package com.atguigu.eduservice.base.service.impl;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.base.client.VodClient;
import com.atguigu.eduservice.base.entity.EduVideo;
import com.atguigu.eduservice.mapper.EduVideoMapper;
import com.atguigu.eduservice.base.service.EduVideoService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-04-14
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Autowired
    private EduVideoService eduVideoService;
    @Autowired
    private VodClient vodClient;

    @Override
    public void removeByCourseId(String courseId) {
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        wrapper.select("video_source_id");
        List<EduVideo> eduVideoList = baseMapper.selectList(wrapper);
        List<String> videoIdList = new ArrayList<>();
        for (int i = 0; i < eduVideoList.size(); i++) {
            EduVideo eduVideo = eduVideoList.get(i);
            String videoId = eduVideo.getVideoSourceId();
            if (!StringUtils.isEmpty(videoId)) {
                videoIdList.add(videoId);
            }
        }
        if (videoIdList.size() != 0)
            vodClient.deleteBatch(videoIdList);

        QueryWrapper<EduVideo> wrapperList = new QueryWrapper<>();
        wrapperList.eq("course_id", courseId);
        baseMapper.delete(wrapperList);
    }

    @Override
    public void removeVod(String id) {
        EduVideo video = eduVideoService.getById(id);
        String videoSourceId = video.getVideoSourceId();
        if (!StringUtils.isEmpty(videoSourceId)) {
            R result = vodClient.removeAlyVideo(videoSourceId);
            if (result.getCode() == 20001) {
                throw new GuliException(20001, "删除异常");
            }
        }
        eduVideoService.removeById(id);
    }
}
