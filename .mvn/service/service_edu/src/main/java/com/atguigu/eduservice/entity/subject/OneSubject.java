package com.atguigu.eduservice.entity.subject;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OneSubject {
    private String id;
    private String title;
    //一个一级分类有多个二级分类
    //这里用到了数据库的一对多关系 ***狂神mybatis课程末尾有讲***
    private List<TwoSubject> children = new ArrayList<>();

}
