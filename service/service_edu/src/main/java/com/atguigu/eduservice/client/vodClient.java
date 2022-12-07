package com.atguigu.eduservice.client;

import com.atguigu.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Component
@FeignClient("service-vod")

public interface vodClient {
    //定义调用方法的名称
    @DeleteMapping("/eduvod/video/removeAlyVideo/{id}" )
    //PathVariable取参数一定要有参数
    public R removeAlyVideo(@PathVariable("id") String id);
    //删除多个视频
    @DeleteMapping("/eduvod/video/delete-batch")
    public R deleteBatch(@RequestParam("videoList") List<String> videoList);

}
