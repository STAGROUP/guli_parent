package com.atguigu.eduorder.service.impl;

import com.atguigu.eduorder.entity.TOrder;
import com.atguigu.eduorder.mapper.TOrderMapper;
import com.atguigu.eduorder.service.TOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author 张于宴
 * @since 2022-12-28
 */
@Service
public class TOrderServiceImpl extends ServiceImpl<TOrderMapper, TOrder> implements TOrderService {

    //1.生成订单方法
    @Override
    public String createOrders(String courseId, String userId) {
        //通过远程调用根据用户id获取用户信息


        //通过远程调用根据课程id获取课程信息
        return null;
    }
}
