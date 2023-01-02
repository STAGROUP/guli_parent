package com.atguigu.staservice.service;

import com.atguigu.staservice.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author 张于宴
 * @since 2023-01-01
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    //统计某一天注册人数 生成统计数据
    void registerCount(String day);

    Map<String, Object> getShowData(String type, String begin, String end);

}
