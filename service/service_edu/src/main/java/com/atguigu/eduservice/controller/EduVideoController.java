package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author 张于宴
 * @since 2022-08-02
 */
@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin//跨域
public class EduVideoController {
    @Autowired
    private EduVideoService eduVideoService;

    //添加小节
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo) {
        eduVideoService.save(eduVideo);
        return R.ok();
    };

    //删除小节
    //TODO 这个方法需要完善 删除小节的时候 同时也要删除视频
    @DeleteMapping("{id}")
    public R deleteVideo(@PathVariable String id) {
        eduVideoService.removeById(id);
        return R.ok();
    }
    ;
    //修改小节
    //根据id查询小节做回显
    @GetMapping("getVideo/{id}")
    public R getVideo(@PathVariable String id){
        EduVideo eduVideo = eduVideoService.getById(id);
        return R.ok().data("video",eduVideo);
    }
    //修改
    @PostMapping("updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo){
        eduVideoService.updateById(eduVideo);
       return R.ok();
    }
}

