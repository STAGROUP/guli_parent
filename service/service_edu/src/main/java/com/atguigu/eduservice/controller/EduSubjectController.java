package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.subject.OneSubject;
import com.atguigu.eduservice.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-07-27
 */
@RestController
@RequestMapping("/eduservice/subject")
@CrossOrigin//解决跨越
public class EduSubjectController {
    @Autowired
    private EduSubjectService subjectService;
    //添加课程分类
    //获取上传过来的文件 把文件内容读取出来
    @PostMapping("addSubject")
    public R addSubject(MultipartFile file){
        //上传过来的excel文件
        subjectService.saveSubject(file,subjectService);
        return R.ok();
    }
    //课程分类列表
    @GetMapping("getAllSubject")
    public R getAllSubject(){
        //list集合泛型是一级分类 因为一级分类有本身也有二级分类集合
        subjectService.getAllOneTwoSubject();
        return R.ok();
    }

}

