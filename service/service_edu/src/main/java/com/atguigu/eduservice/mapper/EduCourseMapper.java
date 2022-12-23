package com.atguigu.eduservice.mapper;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.front.CourseWebVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author 张于宴
 * @since 2022-08-02
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {
public CoursePublishVo getCoursePublishVoById(String courseId);

    CourseWebVo getBaseCourseInfo(String courseId);
}
