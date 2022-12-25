package com.atguigu.oss.Controller;

import com.atguigu.commonutils.R;
import com.atguigu.oss.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 张于宴
 * @version 2019.3
 * @description
 * @date 2022/7/26 12:36
 */
@RestController
@RequestMapping("/eduoss/fileoss")
@CrossOrigin //跨域
public class OssController {
    @Autowired
    private OssService ossService;

    //上传头像的方法
    @PostMapping
    public R uploadOssFile(MultipartFile file) {
        //获取上传的文件 MultipartFile
        String url = ossService.uploadOssFileAvatar(file);
        return R.ok().data("url", url);
    }

}
