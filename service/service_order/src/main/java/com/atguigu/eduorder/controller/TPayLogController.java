package com.atguigu.eduorder.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduorder.service.TPayLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author 张于宴
 * @since 2022-12-28
 */
@RestController
@RequestMapping("/eduorder/payLog")
@CrossOrigin
public class TPayLogController {
    @Autowired
    private TPayLogService payLogService;
    //1.生成微信支付二维码接口
    //参数是订单号
    @GetMapping("createNative/{orderNo}")
    public R createNative(@PathVariable String orderNo){
        //返回信息 包含二维码地址 其他信息
        Map map = payLogService.createNatvie(orderNo);
        return R.ok().data(map);
    }
}

