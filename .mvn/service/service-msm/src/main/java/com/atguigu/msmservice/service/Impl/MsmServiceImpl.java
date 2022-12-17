package com.atguigu.msmservice.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.atguigu.msmservice.service.MsmService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

@Service
public class MsmServiceImpl implements MsmService {
    //发送短信的方法
    @Override
    public boolean send(Map<String, Object> param, String phone) {
        if(StringUtils.isEmpty(phone)) return false;
        DefaultProfile profile =
                DefaultProfile.getProfile("default", "LTAI5tCVMLdNZYEPpazMztF3", "qQCmgkaBOibXJog17smFiNY8Nfap2P");
        IAcsClient client = new DefaultAcsClient(profile);
        //设置相关参数
        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");

        request.putQueryParameter("PhoneNumbers", phone);//设置手机号
        request.putQueryParameter("SignName", "张玉俊的个人网站");//申请阿里云签名
        request.putQueryParameter("TemplateCode", "SMS_264740110");//申请阿里云 模板code
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(param));//验证码数据 转换json格式
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());//
            return response.getHttpResponse().isSuccess();//
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return false;
    }
}
