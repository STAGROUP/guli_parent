package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/eduservice/indexfront")
@CrossOrigin
public class indexFrontController {
    @Autowired
    private EduCourseService courseService;
    @Autowired
    private EduTeacherService teacherService;

    //查询前八条热门课程
    @GetMapping("/selectHotCourse")
    public R selectHotCourse() {
        List<EduCourse> eduList = courseService.selectHotCourse();
        return R.ok().data("eduList", eduList);
    }
    @GetMapping("/selectHotTeacher")
    public R selectHotTeacher() {
        List<EduTeacher> teacherList = teacherService.selectHotTeacher();
        return R.ok().data("teacherList", teacherList);
    }
}
