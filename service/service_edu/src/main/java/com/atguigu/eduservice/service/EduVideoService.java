package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author 张于宴
 * @since 2022-08-02
 */
public interface EduVideoService extends IService<EduVideo> {

    //1.根据课程id删除小节
    void removeVideoByCourseId(String courseId);
}
