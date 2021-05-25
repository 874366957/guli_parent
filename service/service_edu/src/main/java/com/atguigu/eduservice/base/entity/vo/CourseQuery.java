package com.atguigu.eduservice.base.entity.vo;

import lombok.Data;

@Data
public class CourseQuery {
    public String title;
    public Integer status;
    public Integer lessonNum;
    public String beginTime;
    public String endTime;
    public Integer viewCount;
}
