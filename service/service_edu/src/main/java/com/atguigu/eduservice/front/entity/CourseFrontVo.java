package com.atguigu.eduservice.front.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class CourseFrontVo implements Serializable {
    private String title;
    private String teacherId;
    private String subjectParentId;
    private String subjectId;
    private String buyCountSort;
    private String gmtCreateSort;
    private String priceSort;
}
