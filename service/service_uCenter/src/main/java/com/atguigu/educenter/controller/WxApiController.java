package com.atguigu.educenter.controller;

import com.atguigu.educenter.utils.ConstantWxUtils;
import com.atguigu.educenter.utils.HttpClientUtils;
import com.atguigu.servicebase.exceptionhandler.GuliExpception;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@CrossOrigin
@Controller//注意这里没有配置 @RestController
@RequestMapping("/api/ucenter/wx")
public class WxApiController {
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
        } catch (Exception e) {
            e.printStackTrace();
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
