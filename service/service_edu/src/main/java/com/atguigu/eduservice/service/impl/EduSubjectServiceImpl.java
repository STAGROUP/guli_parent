package com.atguigu.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.SubjectData;
import com.atguigu.eduservice.entity.subject.OneSubject;
import com.atguigu.eduservice.entity.subject.TwoSubject;
import com.atguigu.eduservice.listener.SubjectExcelListener;
import com.atguigu.eduservice.mapper.EduSubjectMapper;
import com.atguigu.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author 张于宴
 * @since 2022-07-27
 */
@Service

public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {
    //添加课程分类
    @Override
    public void saveSubject(MultipartFile file, EduSubjectService subjectService) {
        try {
            //文件输入流
            InputStream in = file.getInputStream();
            // 文件流  excel实体类  监听器(核心)
            EasyExcel.read(in, SubjectData.class, new SubjectExcelListener(subjectService)).sheet().doRead();
        } catch (Exception e) {
        }
    }

    //课程分类列表（树形）
    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        //1.查询所以一级分类 parentId=0
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id",0);//eq =
        List<EduSubject> oneSubjectsList = baseMapper.selectList(wrapperOne);
        //2.查询所以二级分类 parentId!=0
        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
        wrapperTwo.ne("parent_id",0);//ne !=
        List<EduSubject> twoSubjectsList = baseMapper.selectList(wrapperTwo);
        //创建list集合 用于存储最终封装的对象
        List<OneSubject> finalSubjectList = new ArrayList<>();
        //3.封装一级分类
        //查询出所有一级分类list集合遍历 得到每一个一级类对象 获取去每一个对象值
        //封装到要求的list集合里面List<OneSubject> finalSubjectList
        for (int i = 0; i <oneSubjectsList.size() ; i++) {
            //得到oneSubjectList每个对象
            EduSubject eduSubject = oneSubjectsList.get(i);
            //把eduSubject里面值取出来 放到oneSubject对象里面
            OneSubject oneSubject = new OneSubject();
            //oneSubject.setId(eduSubject.getId());
            //oneSubject.setTitle(eduSubject.getTitle());
            //工具类BeanUtils 将eduSubject的值git出来set到oneSubject中 等同于上两行代码
            //                        git出来     set进去
            BeanUtils.copyProperties(eduSubject,oneSubject);
            //多个oneSubject放到finalSubjectList里面
            finalSubjectList.add(oneSubject);

            //4.封装二级分类
            //创建list集合封装每个一级分类的二级分类
            List<TwoSubject> twoFinalSubjectList = new ArrayList<>();
            //便利二级分类list集合
            for (int m = 0; m < twoSubjectsList.size(); m++) {
                //获取每个二级分类
                EduSubject tSubject = twoSubjectsList.get(m);
                //判断二级分类parentID跟一级分类id是否相同
                if (tSubject.getParentId().equals(eduSubject.getId())){
                    //把tSubject值复制到TwoSubject里面 放到twoFinalSubjectList里面
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(tSubject,twoSubject);
                    twoFinalSubjectList.add(twoSubject);
                }

            }
            //把一级分类下的所以二级分类放回到一级分类下
            oneSubject.setChildren(twoFinalSubjectList);
        }

        return finalSubjectList;
    }
}
