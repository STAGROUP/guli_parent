package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduCourseDescription;
import com.atguigu.eduservice.entity.front.CourseFrontVo;
import com.atguigu.eduservice.entity.front.CourseWebVo;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.mapper.EduCourseMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduCourseDescriptionService;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.exceptionhandler.GuliExpception;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private EduVideoService eduVideoService;
    @Autowired
    private EduChapterService eduChapterService;

    //添加课程信息
    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        //1.向课程表添加课程基本信息
        //CourseInfoVo=>eduCourse转换对象
        EduCourse eduCourse = new EduCourse();
        //                         get出来       set进去
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        //调用inster
        int insert = baseMapper.insert(eduCourse);
        //返回值=>影响行数 大于0 表示成功  等于0表示失败
        if (insert == 0) {//添加失=>败抛出异常
            throw new GuliExpception(20001, "添加课程信息失败");
        }
        //问题：课程和描述类id应该一致才对 现在不一致为我们应该首先获取id值赋给描述类id

        //获取添加过程后的id
        String cid = eduCourse.getId();

        //2.向课程简介表添加课程简介
        EduCourseDescription courseDescription = new EduCourseDescription();//new 实体类
        courseDescription.setDescription(courseInfoVo.getDescription());//将vo类get出简介并赋值给courseDescription表
        //保证课程id跟课程描述id一致 并将描述实体类id策略改成input 手动设置
        courseDescription.setId(cid);
        courseDescriptionService.save(courseDescription);//调用save方法向数据库中添加数据
        return cid;//返回课程id
    }

    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        //1.查询课程表
        EduCourse eduCourse = baseMapper.selectById(courseId);
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse, courseInfoVo);
        //2.查询描述表
        EduCourseDescription courseDescription = courseDescriptionService.getById(courseId);
        courseInfoVo.setDescription(courseDescription.getDescription());
        System.err.println(courseInfoVo.getDescription());
        return courseInfoVo;
    }

    //修改课程信息
    @Override
    public void updateCourseInfoVo(CourseInfoVo courseInfoVo) {
        //1.修改课程表
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int update = baseMapper.updateById(eduCourse);
        if (update == 0) {
            throw new GuliExpception(20001, "修改课程信息失败");
        }
        //2.修改描述表
        EduCourseDescription description = new EduCourseDescription();
        description.setId(courseInfoVo.getId());
        description.setDescription(courseInfoVo.getDescription());
        courseDescriptionService.updateById(description);
    }

    //根据课程id查询课程确认信息
    @Override
    public CoursePublishVo publishCourseInfo(String id) {
        //调用mapper
        CoursePublishVo coursePublishVoById = baseMapper.getCoursePublishVoById(id);
        return coursePublishVoById;
    }

    //删除课程
    @Override
    public void removeCourse(String courseId) {
        //1.根据课程id删除小节
        eduVideoService.removeVideoByCourseId(courseId);
        //2.根据课程id删除章节
        eduChapterService.removeChapterByCourseId(courseId);
        //3.根据课程id删除描述
        courseDescriptionService.removeById(courseId);
        //4.根据课程id删除课程本身
        int result = baseMapper.deleteById(courseId);
        if(result==0){
            throw new GuliExpception(20001,"删除失败");
        }
    }

    @Override
    @Cacheable(key = "'selectHotCourse'", value = "course")
    public List<EduCourse> selectHotCourse() {
        //查询前8条热门课程
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("view_count");
        wrapper.last("limit 8");
        return baseMapper.selectList(wrapper);
    }

    //前台条件分页查询
    @Override
    public Map<String, Object> getCourseFrontList(Page<EduCourse> pageCourse, CourseFrontVo courseFrontVo) {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper();
        //判断条件值是否为空
        if (!StringUtils.isEmpty(courseFrontVo.getSubjectParentId())){//一级分类
            wrapper.eq("subject_Parent_id",courseFrontVo.getSubjectParentId());
        }
        if (!StringUtils.isEmpty(courseFrontVo.getSubjectId())){//二级分类
            wrapper.eq("subject_id",courseFrontVo.getSubjectId());
        }
        if (!StringUtils.isEmpty(courseFrontVo.getBuyCountSort())){//关注度
            wrapper.orderByDesc("buy_count");
        }
        if (!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort())) {//最新
            wrapper.orderByDesc("gmt_create");
        }

        if (!StringUtils.isEmpty(courseFrontVo.getPriceSort())) {//价格
            wrapper.orderByDesc("price");
        }
        baseMapper.selectPage(pageCourse,wrapper);
        List<EduCourse> records = pageCourse.getRecords();
        long current = pageCourse.getCurrent();
        long pages = pageCourse.getPages();
        long size = pageCourse.getSize();
        long total = pageCourse.getTotal();
        boolean hasNext = pageCourse.hasNext();
        boolean hasPrevious = pageCourse.hasPrevious();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);
        return map;
    }

    //根据课程id 编写sql 语句查询课程信息
    @Override
    public CourseWebVo getBaseCourseInfo(String courseId) {
        return baseMapper.getBaseCourseInfo(courseId);
    }

}
