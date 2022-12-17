package com.atguigu.educenter.controller;


import com.atguigu.commonutils.JwtUtils;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.RegisterVo;
import com.atguigu.educenter.service.UcenterMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.atguigu.commonutils.R;

import javax.servlet.http.HttpServletRequest;

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
    @PostMapping("longin")
    public R loginUser(@RequestBody UcenterMember member){
        String token = memberService.login(member);
        return R.ok().data("token",token);
    }


    //注册
    @PostMapping("register")
    public R registerUser(@RequestBody RegisterVo registerVo){
        memberService.register(registerVo);
        return R.ok();

    }

    //根据token获取用户信息
    @PostMapping("getMembereInfo")
    public R getMemberInfo(HttpServletRequest request){
        //调用jwt工具的方法 根据request对象获取头信息 返回用户id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        //调用数据库 获取请用户信息
        UcenterMember member = memberService.getById(memberId);
        return R.ok().data("userInfo",member);
    }

}

