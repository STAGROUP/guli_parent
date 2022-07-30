package com.atguigu.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.SubjectData;
import com.atguigu.eduservice.service.EduSubjectService;
import com.atguigu.servicebase.exceptionhandler.GuliExpception;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**
 * @author 张于宴
 * @version 2019.3
 * @description 监听器
 * @date 2022/7/27 16:31
 */

public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {
    //因为SubjectExcelListener不能交给spring管理 需要自己new 不能注入其他对象
    //不能实现数据库操作
    public EduSubjectService subjectService;

    //有参构造
    public SubjectExcelListener(EduSubjectService subjectService) {
        this.subjectService = subjectService;
    }

    //无参构造
    public SubjectExcelListener() {
    }

    //读取excel内容 一行一行进行读取
    @Override
    public void invoke(SubjectData data, AnalysisContext context) {
        if(data == null){
            throw new GuliExpception(20001,"文件数据为空");
        }
        //一行一行读取 每次读取都是两个值 第一个值 一级分类 第二个值 二级分类
        EduSubject existOneSubject = this.existOneSubject(subjectService, data.getOneSubjectName());
        if (existOneSubject==null){//没有相同一级分类 进行添加
            existOneSubject = new EduSubject();
            existOneSubject.setParentId("0");
            existOneSubject.setTitle(data.getOneSubjectName());//一级分类名称
            subjectService.save(existOneSubject);
        }
        //添加二级分类
        String pid = existOneSubject.getId();//获取一级分类id值
        EduSubject existTwoSubject = this.existTwoSubject(subjectService, data.getTwoSubjectName(),pid);
        if (existTwoSubject == null){//没有相同一级分类 进行添加
            existTwoSubject = new EduSubject();
            existTwoSubject.setParentId("0");
            existTwoSubject.setTitle(data.getTwoSubjectName());//一级分类名称
            subjectService.save(existTwoSubject);
        }

    }
    //判断一级分类不能重复添加
    private EduSubject existOneSubject(EduSubjectService subjectService,String name){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id",'0');
        EduSubject oneSubject = subjectService.getOne(wrapper);
        return oneSubject;

    }
    //判断二级分类不能重复添加
    private EduSubject existTwoSubject(EduSubjectService subjectService,String name,String pid){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id",pid);
        EduSubject twoSubject = subjectService.getOne(wrapper);
        return twoSubject;

    }
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }
}
