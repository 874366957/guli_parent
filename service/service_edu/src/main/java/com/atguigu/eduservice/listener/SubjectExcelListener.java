package com.atguigu.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.SubjectData;
import com.atguigu.eduservice.service.EduSubjectService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.EventListenerProxy;

public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {
    public EduSubjectService eduSubjectService;

    public SubjectExcelListener() {
    }

    public SubjectExcelListener(EduSubjectService eduSubjectService) {
        this.eduSubjectService = eduSubjectService;
    }

    @Override
    public void invoke(SubjectData data, AnalysisContext context) {
        if (data == null) {
            throw new GuliException(20001, "读取异常");
        }
        EduSubject existFirstSubject = this.existFirstSubject(eduSubjectService, data.getFirstSubjectName());
        if (existFirstSubject == null) {
            existFirstSubject = new EduSubject();
            existFirstSubject.setParentId("0");
            existFirstSubject.setTitle(data.getFirstSubjectName());
            eduSubjectService.save(existFirstSubject);
        }
        String parentId = existFirstSubject.getId();
        EduSubject existSecondSubject = this.existSecondSubject(eduSubjectService, data.getSecondSubjectName(), parentId);
        if (existSecondSubject == null) {
            existSecondSubject = new EduSubject();
            existSecondSubject.setParentId(parentId);
            existSecondSubject.setTitle(data.getSecondSubjectName());
            eduSubjectService.save(existSecondSubject);
        }
    }

    private EduSubject existFirstSubject(EduSubjectService eduSubjectService, String name) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id", 0);
        EduSubject oneSubject = eduSubjectService.getOne(wrapper);
        return oneSubject;
    }

    private EduSubject existSecondSubject(EduSubjectService eduSubjectService, String name, String parentId) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id", parentId);
        EduSubject secondSubject = eduSubjectService.getOne(wrapper);
        return secondSubject;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }
}
