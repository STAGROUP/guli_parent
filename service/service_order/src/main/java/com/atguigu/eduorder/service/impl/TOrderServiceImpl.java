package com.atguigu.eduorder.service.impl;

import com.atguigu.commonutils.orderVo.CourseWebVoOrder;
import com.atguigu.commonutils.orderVo.UcenterMemberOrder;
import com.atguigu.eduorder.client.EduClient;
import com.atguigu.eduorder.client.UcenterClient;
import com.atguigu.eduorder.utils.OrderNoUtil;
import com.atguigu.eduorder.entity.TOrder;
import com.atguigu.eduorder.mapper.TOrderMapper;
import com.atguigu.eduorder.service.TOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private EduClient eduClient;//注入远程方法
    @Autowired
    private UcenterClient ucenterClient;
    //1.生成订单方法
    @Override
    public String createOrders(String courseId, String userId) {
        //通过远程调用根据用户id获取用户信息
        UcenterMemberOrder memberInfoOrder = ucenterClient.getMemberInfoOrder(userId);
        //通过远程调用根据课程id获取课程信息
        CourseWebVoOrder courseIOInfoOrder = eduClient.getCourseIOInfoOrder(courseId);
        //创建order对象 向order对象里面设置值
        TOrder tOrder = new TOrder();
        tOrder.setOrderNo(OrderNoUtil.getOrderNo());
        tOrder.setCourseId(courseId);
        tOrder.setCourseTitle(courseIOInfoOrder.getTitle());
        tOrder.setCourseCover(courseIOInfoOrder.getCover());
        tOrder.setTeacherName(courseIOInfoOrder.getTeacherName());
        tOrder.setTotalFee(courseIOInfoOrder.getPrice());
        tOrder.setMemberId(userId);
        tOrder.setMobile(memberInfoOrder.getMobile());
        tOrder.setNickname(memberInfoOrder.getNickname());
        tOrder.setStatus(0);//订单状态 0未支付 1已支付
        tOrder.setPayType(1);//支付类型 1微信
        baseMapper.insert(tOrder);//使用inster插入一个对象
        //返回订单号
        return tOrder.getOrderNo();
    }
}
