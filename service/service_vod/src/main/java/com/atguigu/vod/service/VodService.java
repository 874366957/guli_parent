package com.atguigu.vod.service;

import com.aliyuncs.exceptions.ClientException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VodService {
    String uploadVideo(MultipartFile file);

    void deleteVideo(String id) throws ClientException;

    void deleteCourseVideo(List videoIdList);
}
