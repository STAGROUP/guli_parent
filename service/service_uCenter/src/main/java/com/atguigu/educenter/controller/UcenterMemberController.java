package com.atguigu.educenter.controller;


import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.service.UcenterMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.atguigu.commonutils.R;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author STA_张于宴
 * @since 2022-12-17
 */
@RestController
@RequestMapping("/educenter/member")
@CrossOrigin
public class UcenterMemberController {
    @Autowired
    private UcenterMemberService memberService;
    //登录
    @PostMapping("/longin")
    public R loginUser(@RequestBody UcenterMember member){
        String token = memberService.login(member);
        return R.ok().data("token",token);
    }


    //注册

}
