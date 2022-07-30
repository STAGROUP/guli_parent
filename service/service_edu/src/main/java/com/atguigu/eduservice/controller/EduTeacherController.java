package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.vo.TeacherQuery;
import com.atguigu.eduservice.service.EduTeacherService;
import com.atguigu.servicebase.exceptionhandler.GuliExpception;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-06-28
 */
@Api(description = "讲师管理")
@RestController
@RequestMapping("/eduservice/edu-teacher")
@CrossOrigin//解决跨越
public class EduTeacherController {

    //把service注入
    @Autowired
    private EduTeacherService teacherService;

    //1.查询讲师所有数据
    @ApiOperation(value = "所有讲师列表")
    @GetMapping("findAll")
    public R findAllTeacher() {
        List<EduTeacher> list = teacherService.list(null);
//        try {
//            int i = 10/0;
//        }catch (Exception e){
//            throw new GuliExpception(20001,"执行了自定义异常");
//        }
        return R.ok( ).data("items", list);
    }

    //2.讲师删除
    @ApiOperation(value = "逻辑删除讲师")
    @DeleteMapping("{id}")
    public R removeTeacher(@ApiParam(name = "id", value = "讲师ID", required = true) @PathVariable String id) {
        boolean flag = teacherService.removeById(id);
        if (flag) {
            return R.ok( );
        } else {
            return R.error( );

        }
    }

    //3.分页查询讲师的方法
    @ApiOperation(value = "分页查询讲师")
    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageListTeacher(@PathVariable long current,
                             @PathVariable long limit) {
        Page<EduTeacher> pageTeacher = new Page<>(current, limit);
        teacherService.page(pageTeacher, null);
        long total = pageTeacher.getTotal( );//总记录数
        List<EduTeacher> records = pageTeacher.getRecords( );//数据记录数
        return R.ok( ).data("total", total).data("rows", records);
    }

    //4.带条件查询的分页
    @ApiOperation(value = "带条件的分页查询")
    @PostMapping("pageTeacheCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable long current, @PathVariable long limit,
                                  @RequestBody(required = false) TeacherQuery teacherQuery) {
        //创建一个page对象
        Page<EduTeacher> pageTeacher = new Page<>(current, limit);
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>( );
        String begin = teacherQuery.getBegin( );
        Integer level = teacherQuery.getLevel( );
        String name = teacherQuery.getName( );
        String end = teacherQuery.getEnd( );
        if (!StringUtils.isEmpty(name)) {
            wrapper.like("name", name);
        }
        if (!StringUtils.isEmpty(level)) {
            wrapper.eq("level", level);
        }
        if (!StringUtils.isEmpty(begin)) {
            wrapper.ge("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            wrapper.le("gmt_create", end);
        }
        //排序
        wrapper.orderByDesc("gmt_create");
        teacherService.page(pageTeacher, wrapper);
        long total = pageTeacher.getTotal( );//总记录数
        List<EduTeacher> records = pageTeacher.getRecords( );//数据记录数
        return R.ok( ).data("total", total).data("rows", records);
    }

    //5.添加讲师接口的方法
    @ApiOperation(value = "添加讲师方法", notes = "详细描述")
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean save = teacherService.save(eduTeacher);
        if (save) {
            return R.ok( );
        } else {
            return R.error( );
        }
    }

    //6.
    @GetMapping("getTeacher/{id}")
    @ApiOperation(value = "根据id查询讲师 ")
    public R getTeacher(@ApiParam(name = "id", value = "讲师id", required = true) @PathVariable String id) {
        //根据讲师id进行查询
        EduTeacher eduTeacher = teacherService.getById(id);
        return R.ok( ).data("teacher", eduTeacher);
    }

    //讲师修改
    @PostMapping("updateTeacher")
    @ApiOperation(value = "讲师修改")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean flag = teacherService.updateById(eduTeacher);

        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

}

