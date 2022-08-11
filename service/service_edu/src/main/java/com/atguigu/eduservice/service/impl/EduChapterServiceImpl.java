package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.atguigu.eduservice.entity.chapter.VideoVo;
import com.atguigu.eduservice.mapper.EduChapterMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.velocity.util.ArrayListWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author 张于宴
 * @since 2022-08-02
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {
    @Autowired
    private EduVideoService eduVideoService;

    //课程大纲列表 根据id进行查询
    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
       //1.根据课程id查询课程里面的所有章节
        QueryWrapper<EduChapter> wrapperChapter = new QueryWrapper<>();
        wrapperChapter.eq("course_id",courseId);
        List<EduChapter> eduChapters = baseMapper.selectList(wrapperChapter);

        // 2.根据课程id查询课程里面的所有小节
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id",courseId);
        List<EduVideo> eduVideoList = eduVideoService.list(wrapperVideo);

        //创建list集合 用于封装
        List<ChapterVo> finalList = new ArrayList<>();
        //3.遍历查询章节list集合进行封装
        //查询章节list集合
        for (int i = 0; i < eduChapters.size(); i++) {
            //每个章节
            EduChapter eduChapter = eduChapters.get(i);
            //eduChapter对象复制到ChapterVO里面
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter,chapterVo);
            //把chapterVo放到最终list集合
            finalList.add(chapterVo);
            //创建集合 用于封装章节的小节
            List<VideoVo> videoList  = new ArrayList<>();
            //4.遍历查询小节list集合 进行封装
            for (int m = 1; m < eduVideoList.size(); m++) {
                //获得每个小节
                EduVideo eduVideo = eduVideoList.get(i);
                //判断 小节里面的章节id是否跟章节的id语一样
                if (eduVideo.getChapterId().equals(eduChapter.getId())){
                    //进行封装
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo,videoVo);
                    //放到小节封装的集合
                    videoList.add(videoVo);
                }
            }
            //把封装之后的小节list集合 放到章节对象里面
            chapterVo.setChildren(videoList);
        }
        return null;
    }
}
