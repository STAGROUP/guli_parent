package com.atguigu.oss.service;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.CannedAccessControlList;
import com.atguigu.oss.utils.ConstanPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

/**
 * @author 张于宴
 * @version 2019.3
 * @description
 * @date 2022/7/26 12:35
 */
@Service
public class OssServiceImpl implements OssService {
    @Override
    //上传头像到oss
    public String uploadOssFileAvatar(MultipartFile file) {
        //获取阿里云存储相关常量
        String endPoint = ConstanPropertiesUtils.END_POINT;//地狱节点
        String accessKeyId = ConstanPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstanPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketName = ConstanPropertiesUtils.BUCKET_NAME;

        try {
            //创建Oss实例
            OSS ossClient = new OSSClientBuilder( ).build(endPoint, accessKeyId, accessKeySecret);
            //获取上传文件输入流
            InputStream inputStream = file.getInputStream( );
            //获取文件名称
            String Filename = file.getOriginalFilename( );
            //在文件名称添加唯一的随机值
            String uuid = UUID.randomUUID( ).toString( ).replaceAll("-","");
            Filename = uuid+Filename;
            //把文件按日期分类
            String datePath = new DateTime( ).toString("yyyy/MM/dd");
            //拼接
            Filename = datePath+"/"+Filename;
            //调用oss方法是实现上传
            //bucket名称 上传到oss文件路径和文件名称
            ossClient.putObject(bucketName, Filename, inputStream);
            //关闭OSSClient
            ossClient.shutdown( );
            //上传之后路径返回
            //需要把上传到阿里云oss路径手动拼出来
            String url = "https://"+bucketName+"."+endPoint+"/"+Filename;
            return  url;
        } catch (IOException ex) {
            ex.printStackTrace();
            return  null;
        }
    }
}
