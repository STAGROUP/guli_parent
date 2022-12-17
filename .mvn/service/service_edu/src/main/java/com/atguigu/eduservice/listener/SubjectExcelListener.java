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
    //监听器要用到EduSubjectServiceImpl的方法但是又不能直接注入service层 所以只能-----
    //不能实现数据库操作 就不能实现数据库的查询和添加
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
    //一级分类的pid 为 0
    //二级分类的pid 为 一级分类的 id 通过getId()获取一级分类id
    public void invoke(SubjectData data, AnalysisContext context) {
        if(data == null){//先判断excel里面有无数据 若无  抛出自定义异常
            throw new GuliExpception(20001,"文件数据为空");
        }
        //一行一行读取 每次读取都是两个值 第一个值 一级分类 第二个值 二级分类
        EduSubject existOneSubject = this.existOneSubject(subjectService, data.getOneSubjectName());
        if (existOneSubject==null){//没有相同一级分类 进行添加
            existOneSubject = new EduSubject();//创建对象
            existOneSubject.setParentId("0");//传入pid为0 默认的
            existOneSubject.setTitle(data.getOneSubjectName());//一级分类名称
            //mp中的save方法先根据id id=>为空 执行insert   id=>不为空 执行update
            subjectService.save(existOneSubject);//传入对象执行save方法进行添加
        }
        //添加二级分类
        String pid = existOneSubject.getId();//获取一级分类id值
        EduSubject existTwoSubject = this.existTwoSubject(subjectService, data.getTwoSubjectName(),pid);
        if (existTwoSubject == null){//没有相同一级分类 进行添加
            existTwoSubject = new EduSubject();//创建对象
            existTwoSubject.setParentId(pid);//设置二级分类的pid为一级分类的id
            existTwoSubject.setTitle(data.getTwoSubjectName());//一级分类名称
            subjectService.save(existTwoSubject);//传入对象执行save方法进行添加
        }

    }
    //判断一级分类不能重复添加 只能添加一次
    private EduSubject existOneSubject(EduSubjectService subjectService,String name){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id",'0');//一级分类pid默认为0
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
