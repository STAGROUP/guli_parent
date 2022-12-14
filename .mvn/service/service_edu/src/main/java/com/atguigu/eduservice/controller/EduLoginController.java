package com.atguigu.eduservice.controller;

import com.atguigu.commonutils.R;
import org.springframework.web.bind.annotation.*;

/**
 * @author 张于宴
 * @version 2019.3
 * @description
 * @date 2022/7/21 2:49
 */

@RestController
@RequestMapping("/eduservice/user")
@CrossOrigin//解决跨越
public class EduLoginController {
    //login
    @PostMapping("login")
    public R login(){

        return R.ok( ).data("token","admin");
    }
    //info
    @GetMapping("info")
    public R info(){

        return R.ok( ).data("roles","[admin]").data("name","admin").data("avatar","https://ssg-gulixueyuan.oss-cn-beijing.aliyuncs.com/20200421165327_SMmfX.gif");
    }
}
