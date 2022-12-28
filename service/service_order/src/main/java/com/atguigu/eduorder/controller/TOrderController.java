package com.atguigu.eduorder.controller;


import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.eduorder.client.EduClient;
import com.atguigu.eduorder.client.UcenterClient;
import com.atguigu.eduorder.service.TOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author 张于宴
 * @since 2022-12-28
 */
@RestController
@RequestMapping("/eduorder/order")
@CrossOrigin
public class TOrderController {
    @Autowired
    private TOrderService orderService;

    //1.生成订单方法
    @PostMapping("createOrder/{courseId}")
    public R createOrder(@PathVariable String courseId, HttpServletRequest request){
        //header中获取token返回用户id
        String UserId = JwtUtils.getMemberIdByJwtToken(request);
        //创建订单 返回订单号
        String orderNo = orderService.createOrders(courseId,UserId);
        return R.ok().data("orderId",orderNo);
    }
}

