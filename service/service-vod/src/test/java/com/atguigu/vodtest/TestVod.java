package com.atguigu.vodtest;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;

import java.util.List;
//根据视频id获取视频地址
public class TestVod {
    public static void main(String[] args) throws ClientException {
        //1.根据视频id获取播放地址
        DefaultAcsClient client = initObject.initVodClient("LTAI5tCVMLdNZYEPpazMztF3", "qQCmgkaBOibXJog17smFiNY8Nfap2P");
        //创建初始化对象
        //创建获取视频地址request何response
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        GetPlayInfoResponse response = new GetPlayInfoResponse();
        //向request对象里面设置视频id
        request.setVideoId("59d02a5531ba464bb8e1110cf9a91d3e");
        //调用初始化对象里面的方法传递request获取数据
        response = client.getAcsResponse(request);

        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
        //播放地址
        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
            System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
        }
        //Base信息
        System.out.print ("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");

}
}
