package com.atguigu.staservice.schedule;

import com.atguigu.staservice.service.StatisticsDailyService;
import com.atguigu.staservice.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ScheduledTask {
    @Autowired
    private StatisticsDailyService staService;
    /**
     * 测试
     * 每天凌晨一点把前一天数据进行数据查询添加执行一次
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void task1() {
        staService.registerCount(DateUtil.formatDate(DateUtil.addDays(new Date(),-1)));
    }

}
