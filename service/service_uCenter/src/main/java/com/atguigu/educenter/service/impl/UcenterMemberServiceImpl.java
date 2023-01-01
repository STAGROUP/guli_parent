package com.atguigu.educenter.service.impl;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.MD5;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.RegisterVo;
import com.atguigu.educenter.mapper.UcenterMemberMapper;
import com.atguigu.educenter.service.UcenterMemberService;
import com.atguigu.servicebase.exceptionhandler.GuliExpception;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author STA_张于宴
 * @since 2022-12-17
 */
@Service
@CrossOrigin
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override //登陆方法
    public String login(UcenterMember member) {
        //获取登陆的手机号和密码
        String mobile = member.getMobile();
        String password = member.getPassword();
        //手机号和密码非空判断
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            throw new GuliExpception(20001, "登陆失败");
        }
        //判断手机号是否为真
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        //根据输入的手机号跟数据库对比查得到就进行数据库擦查询褚这条数据在做判断
        UcenterMember mobileMember = baseMapper.selectOne(wrapper);
        //判断查询对象是否为空
        if (mobileMember == null) {
            throw new GuliExpception(20001, "手机号不存在");
        }
        //判断密码 数据库密码是加密的密码 把我们输入的密码进行加密 在和数据对比
        //加密方式 MD5
        if (!MD5.encrypt(password).equals(mobileMember.getPassword())) {
            throw new GuliExpception(20001, "密码错误");
        }
        //判断账号是否被禁用
        if (mobileMember.getIsDisabled()) {
            throw new GuliExpception(20001, "账号已禁用");
        }
        //登录成功 使用jwt工具类
        String jwtToken = JwtUtils.getJwtToken(mobileMember.getId(), mobileMember.getNickname());

        return jwtToken;
    }


    //注册
    @Override
    public void register(RegisterVo registerVo) {
        String code = registerVo.getCode();//验证码
        String mobile = registerVo.getMobile();
        String nickname = registerVo.getNickname();
        String password = registerVo.getPassword();
        //非空判断
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password) ||
                StringUtils.isEmpty(code) || StringUtils.isEmpty(nickname)) {
            throw new GuliExpception(20001, "注册失败");
        }
        //验证码判断
        String redisCode = redisTemplate.opsForValue().get(mobile);
        if (!code.equals(redisCode)) {
            throw new GuliExpception(20001, "验证码失效！");
        }
        //判断手机号是否重复
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        Integer count = baseMapper.selectCount(wrapper);
        if (count > 0) {
            throw new GuliExpception(20001, "手机号已被注册过了");
        }
        UcenterMember ucenterMember = new UcenterMember();
        ucenterMember.setMobile(mobile);
        ucenterMember.setPassword(MD5.encrypt(password));
        ucenterMember.setIsDisabled(false);
        ucenterMember.setAvatar("https://ssg-gulixueyuan.oss-cn-beijing.aliyuncs.com/2022/08/02/2b55ddb3fd1d4edd850aa9320ae202a6file.png");
        baseMapper.insert(ucenterMember);
    }

    //根据openid进行查询
    @Override
    public UcenterMember getOpenIdMember(String openid) {
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("openid", openid);
        UcenterMember member = baseMapper.selectOne(wrapper);
        return member;
    }

    //查询某一天的注册人数
    @Override
    public Integer countRegister(String day) {
        return baseMapper.countRegisterDay(day);
    }
}
