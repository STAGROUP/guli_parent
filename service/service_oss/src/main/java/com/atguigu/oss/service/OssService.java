package com.atguigu.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author 张于宴
 * @version 2019.3
 * @description
 * @date 2022/7/26 12:35
 */

public interface OssService {
   //上传头像的方法
    public String uploadOssFileAvatar(MultipartFile file);
}
