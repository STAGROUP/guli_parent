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
    //2.查询订单状态
    @GetMapping("queryPayStatus/{orderNo}")
    public R queryPayStatus(@PathVariable String orderNo){
        Map<String,String> map = payLogService.queryPayStatus(orderNo);
        if (map==null){
            return R.error().message("支付出错了");
        }
        //如果返回不为空 通过map获取订单状态
        if (map.get("trade_state").equals("SUCCESS")){//支付成功
            //向支付表添加记录 并更新订单状态
            payLogService.updateOrderStatus(map);
            return R.ok().message("支付成功");
        }
        return R.ok().message("支付中");
    }
}

