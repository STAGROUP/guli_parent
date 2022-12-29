package com.atguigu.eduorder.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.eduorder.entity.TOrder;
import com.atguigu.eduorder.entity.TPayLog;
import com.atguigu.eduorder.mapper.TPayLogMapper;
import com.atguigu.eduorder.service.TOrderService;
import com.atguigu.eduorder.service.TPayLogService;
import com.atguigu.eduorder.utils.HttpClient;
import com.atguigu.servicebase.exceptionhandler.GuliExpception;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.wxpay.sdk.WXPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author 张于宴
 * @since 2022-12-28
 */
@Service
public class TPayLogServiceImpl extends ServiceImpl<TPayLogMapper, TPayLog> implements TPayLogService {
    @Autowired
    private TOrderService orderService;

    //生成微信支付二维码接口
    @Override
    public Map createNatvie(String orderNo) {
        try {
            //1根据订单查询订单信息
            QueryWrapper<TOrder> wrapper = new QueryWrapper<>();
            wrapper.eq("order_No", orderNo);
            TOrder order = orderService.getOne(wrapper);
            //2使用map设置生成二维码的参数
            Map m = new HashMap();
            m.put("appid", "wx74862e0dfcf69954");
            m.put("mch_id", "1558950191");
            //工具类随机生成字符串
            m.put("nonce_str", WXPayUtil.generateNonceStr());
            m.put("body", order.getCourseTitle());
            m.put("out_trade_no", orderNo);
            m.put("total_fee", order.getTotalFee().multiply(new BigDecimal("100")).longValue() + "");
            m.put("spbill_create_ip", "127.0.0.1");
            m.put("notify_url", "http://guli.shop/api/order/weixinPay/weixinNotify\n");
            m.put("trade_type", "NATIVE");
            //3发送HTTP client请求 传递参数xml格式 微信支付提供固定的地址
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
            //设置xml格式参数
            client.setXmlParam(WXPayUtil.generateSignedXml(m, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);
            //执行请求发送
            client.post();
            //4得到发送请求返回结果
            //返回参数书 是xml格式
            String content = client.getContent();
            Map<String, String> resultMap = WXPayUtil.xmlToMap(content);
            //最终返回数据
            Map map = new HashMap<>();
            map.put("out_trade_no", orderNo);
            map.put("course_id", order.getCourseId());
            map.put("total_fee", order.getTotalFee());
            map.put("result_code", resultMap.get("result_code"));//返回二维码操作状态码
            map.put("code_url", resultMap.get("code_url"));//二维码地址
            return map;
        } catch (Exception e) {
            throw new GuliExpception(20001, "生成二维码失败");
        }
    }

    //根据订单号查询订单状态
    @Override
    public Map<String, String> queryPayStatus(String orderNo) {
        try {
            //1、封装参数
            Map m = new HashMap<>();
            m.put("appid", "wx74862e0dfcf69954");
            m.put("mch_id", "1558950191");
            m.put("out_trade_no", orderNo);
            m.put("nonce_str", WXPayUtil.generateNonceStr());

            //2、设置请求
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
            client.setXmlParam(WXPayUtil.generateSignedXml(m, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);
            client.post();
            //3、返回第三方的数据
            String xml = client.getContent();
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);
            //6、转成Map
            //7、返回
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    //向支付表中添加记录 同时更新订单状态
    @Override
    public void updateOrderStatus(Map<String, String> map) {
        //获取订单id
        String orderNo = map.get("out_trade_no");
        //根据订单id查询订单信息
        QueryWrapper<TOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderNo);
        TOrder order = orderService.getOne(wrapper);
        //判断订单状态是不是1 是1直接返回 不是1 设置成1
        //intValue string 转 int
        if(order.getStatus().intValue() == 1) return;
        order.setStatus(1);
        orderService.updateById(order);

        //记录支付日志
        TPayLog payLog=new TPayLog();
        payLog.setOrderNo(order.getOrderNo());//支付订单号
        payLog.setPayTime(new Date());
        payLog.setPayType(1);//支付类型
        payLog.setTotalFee(order.getTotalFee());//总金额(分)
        payLog.setTradeState(map.get("trade_state"));//支付状态
        payLog.setTransactionId(map.get("transaction_id"));
        payLog.setAttr(JSONObject.toJSONString(map));
        baseMapper.insert(payLog);//插入到支付日志表
    }
}
