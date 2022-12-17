package com.atguigu.eduservice.entity.chapter;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
//章节id
@Data
public class ChapterVo {

    private String id;
    private String title;

    //表示小节 一对多
    private List<VideoVo> children = new ArrayList<>();
}
