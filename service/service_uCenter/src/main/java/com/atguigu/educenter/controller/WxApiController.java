package com.atguigu.educenter.controller;

import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.service.UcenterMemberService;
import com.atguigu.educenter.utils.ConstantWxUtils;
import com.atguigu.educenter.utils.HttpClientUtils;
import com.atguigu.servicebase.exceptionhandler.GuliExpception;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

@CrossOrigin
@Controller//注意这里没有配置 @RestController
@RequestMapping("/api/ucenter/wx")
public class WxApiController {
    @Autowired
    private UcenterMemberService ucenterMemberService;
    //2.获取扫描人信息
    @GetMapping("callback")
    public String callback(String code,String state){

        try {
            //1.先获取code值 临时票据 类似于验证码

            //2.拿着code值 请求微信固定地址得到两个值 access_token 和openID
            String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                    "?appid=%s" +
                    "&secret=%s" +
                    "&code=%s" +
                    "&grant_type=authorization_code";

            String accessTokenUrl = String.format(
                    baseAccessTokenUrl,
                    ConstantWxUtils.WX_OPEN_APP_ID,
                    ConstantWxUtils.WX_OPEN_APP_SECRET,
                    code);
            //请求这个拼接好得多地址 得到两个返回的值 access_Token 和 openID
            //httpclient发送请求 得到返回结果 不用浏览器请求
            String accessToken = HttpClientUtils.get(accessTokenUrl);
            System.out.println(accessToken);
            //从accessToken字符串中拿出accessToken和openid
            //把accessToken转成map集合 根据key获取value
            //使用gson工具
            Gson gson = new Gson();
            HashMap MapAccessToken = gson.fromJson(accessToken, HashMap.class);
            String accestoken = (String) MapAccessToken.get("access_token");
            String openid = (String) MapAccessToken.get("openid");
            //3.拿着access_token openid 去请求微信固定地址 获取扫码人信息
            String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                    "?access_token=%s" +
                    "&openid=%s";
            String userInfoUrl = String.format(baseUserInfoUrl, accestoken, openid);
            String userInfo = HttpClientUtils.get(userInfoUrl);
            System.out.println(userInfo);
            //获取扫码人信息
            HashMap MapUserInfo = gson.fromJson(userInfo, HashMap.class);
            String nickname = (String) MapUserInfo.get("nickname");
            String headimgurl = (String) MapUserInfo.get("headimgurl");
            //扫码人信息添加到数据库
            //判断数据库里面是否存在相同微信的信息 根据openid
            UcenterMember member = ucenterMemberService.getOpenIdMember(openid);
            if (member==null){ //member为null 进行添加
                 member = new UcenterMember();
                member.setOpenid(openid);
                member.setNickname(nickname);
                member.setAvatar(headimgurl);
                ucenterMemberService.save(member);
            }
        } catch (Exception e) {
            throw  new GuliExpception(20001,"登陆失败");
        }
        return "redirect:http://localhost:3000";
    }
    //1.生成二维码
    @GetMapping("login")
    public String getWxCode(){
        //固定地址 后面拼接参数
        // 微信开放平台授权baseUrl
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";
        //对redirect_url进行URLEncoder编码
        String redirectUrl = ConstantWxUtils.WX_OPEN_REDIRECT_URL;
        try {
            redirectUrl = URLEncoder.encode(redirectUrl, "utf-8");
        }catch (UnsupportedEncodingException e) {
            throw new GuliExpception(20001, e.getMessage());
        }
       String url = String.format(
                baseUrl,
                ConstantWxUtils.WX_OPEN_APP_ID,
                redirectUrl,
                "atguigu"
        );

        return "redirect:" + url;
    }
}
