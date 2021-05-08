package com.atguigu.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.SubjectData;
import com.atguigu.eduservice.entity.subject.FirstSubject;
import com.atguigu.eduservice.entity.subject.SecondSubject;
import com.atguigu.eduservice.listener.SubjectExcelListener;
import com.atguigu.eduservice.mapper.EduSubjectMapper;
import com.atguigu.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.util.BeanUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-04-13
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public void saveSubject(MultipartFile file, EduSubjectService eduSubjectService) {
        try {
            InputStream in = file.getInputStream();
            EasyExcel.read(in, SubjectData.class, new SubjectExcelListener(eduSubjectService)).sheet().doRead();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<FirstSubject> getAllFirstSecondSubject() {
        QueryWrapper<EduSubject> wrapperFirst = new QueryWrapper<>();
        wrapperFirst.eq("parent_id", "0");
        List<EduSubject> firstSubjectsList = baseMapper.selectList(wrapperFirst);
        QueryWrapper<EduSubject> wrapperSecond = new QueryWrapper<>();
        wrapperFirst.ne("parent_id", "0");
        List<EduSubject> secondSubjectsList = baseMapper.selectList(wrapperSecond);
        List<FirstSubject> finalSubjectList = new ArrayList<>();

        for (int i = 0; i < firstSubjectsList.size(); i++) {
            EduSubject eduSubject = firstSubjectsList.get(i);
            FirstSubject firstSubject = new FirstSubject();
            BeanUtils.copyProperties(eduSubject, firstSubject);
            finalSubjectList.add(firstSubject);
            List<SecondSubject> secondFinalList = new ArrayList<>();
            for (int m = 0; m < secondSubjectsList.size(); m++) {
                EduSubject secondEduSubject = secondSubjectsList.get(m);
                if (secondEduSubject.getParentId().equals(eduSubject.getId())) {
                    SecondSubject secondSubject = new SecondSubject();
                    BeanUtils.copyProperties(secondEduSubject, secondSubject);
                    secondFinalList.add(secondSubject);
                }
            }
            firstSubject.setChildren(secondFinalList);
        }

        return finalSubjectList;
    }
}
