package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduCourseDescription;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.mapper.EduCourseMapper;
import com.atguigu.eduservice.service.EduCourseDescriptionService;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.servicebase.exceptionhandler.GuliExpception;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author 张于宴
 * @since 2022-08-02
 * 将vo类表向课程表和简介表中添加数据
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    //eduCourse想使用courseDescriptionService注入进来调用
    @Autowired
    private EduCourseDescriptionService courseDescriptionService;

    //添加课程信息
    @Override
    public void saveCourseInfo(CourseInfoVo courseInfoVo) {
        //1.向课程表添加课程基本信息
        //CourseInfoVo=>eduCourse转换对象
        EduCourse eduCourse = new EduCourse();
        //                         get出来       set进去
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        //调用inster
        int insert = baseMapper.insert(eduCourse);
        //返回值=>影响行数 大于0 表示成功  等于0表示失败
        if (insert == 0){//添加失=>败抛出异常
            throw new GuliExpception(20001,"添加课程信息失败");
        }
        //问题：课程和描述类id应该一致才对 现在不一致为我们应该首先获取id值赋给描述类id

        //获取添加过程后的id
        String cid = eduCourse.getId();

        //2.向课程简介表添加课程简介
        EduCourseDescription courseDescription = new EduCourseDescription();//new 实体类
        courseDescription.setDescription(courseInfoVo.getDescription());//将vo类get出简介并赋值给courseDescription表
        //保证课程id跟课程描述id一致 并将描述实体类id策略改成input 手动设置
        courseDescription.setId(cid);
        courseDescriptionService.save(courseDescription );//调用save方法向数据库中添加数据
    }
}
