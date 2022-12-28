package com.atguigu.eduorder.client;

import com.atguigu.commonutils.orderVo.CourseWebVoOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient("service-edu")
public interface EduClient {
    @PostMapping("/eduservice/coursefront/getCourseIOInfoOrder/{id}")
    public CourseWebVoOrder getCourseIOInfoOrder(@PathVariable("id") String id);

}
