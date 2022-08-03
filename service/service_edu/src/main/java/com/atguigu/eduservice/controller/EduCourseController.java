package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-08-02
 */
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin//跨域
public class EduCourseController {
    @Autowired
    private EduCourseService courseService;
    //添加课程基本信息
    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        courseService.saveCourseInfo(courseInfoVo);//调用service层写具体过程
        return R.ok();
    }

}

