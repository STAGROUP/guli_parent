package com.atguigu.eduorder.service;

import com.atguigu.eduorder.entity.TPayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author 张于宴
 * @since 2022-12-28
 */
public interface TPayLogService extends IService<TPayLog> {

    Map createNatvie(String orderNo);

    //根据订单号查询订单状态
    Map<String, String> queryPayStatus(String orderNo);
    //向支付表中添加记录 同时更新订单状态
    void updateOrderStatus(Map<String, String> map);
}
