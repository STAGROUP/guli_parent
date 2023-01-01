package com.atguigu.staservice.service.impl;

import com.atguigu.commonutils.R;
import com.atguigu.commonutils.orderVo.UcenterMemberOrder;
import com.atguigu.staservice.client.UcenterClient;
import com.atguigu.staservice.entity.StatisticsDaily;
import com.atguigu.staservice.mapper.StatisticsDailyMapper;
import com.atguigu.staservice.service.StatisticsDailyService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author 张于宴
 * @since 2023-01-01
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {

    @Autowired
    private UcenterClient ucenterClient;

    @Override
    //统计某一天注册人数 生成统计数据
    public void registerCount(String day) {
        //添加记录之前删除相同日期的数据
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<StatisticsDaily>();
        wrapper.eq("date_calculated",day);
        baseMapper.delete(wrapper);

        //远程掉用
        R registerR = ucenterClient.countRegister(day);
        Integer countRegister = (Integer) registerR.getData().get("countRegister");

        Integer loginNum = RandomUtils.nextInt(100,200);
        Integer videoViewNum = RandomUtils.nextInt(100, 200);
        Integer courseNum = RandomUtils.nextInt(100, 200);
        //把数据添加到数据库 统计分析表里面
        StatisticsDaily statisticsDaily = new StatisticsDaily();
        statisticsDaily.setRegisterNum(countRegister);
        statisticsDaily.setDateCalculated(day);
        statisticsDaily.setLoginNum(loginNum);
        statisticsDaily.setVideoViewNum(videoViewNum);
        statisticsDaily.setCourseNum(courseNum);
        baseMapper.insert(statisticsDaily);

    }
}
