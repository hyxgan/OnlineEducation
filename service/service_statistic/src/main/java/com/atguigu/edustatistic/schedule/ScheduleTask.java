package com.atguigu.edustatistic.schedule;


import com.atguigu.edustatistic.service.StatisticsDailyService;
import com.atguigu.edustatistic.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;


/**
 * @auther hyx
 */
@Component
public class ScheduleTask {
        @Autowired
    StatisticsDailyService staService;

//        每天凌晨一点统计前一天的注册人数
//    写六位，第七位“年”省略。写了报错。
    @Scheduled(cron = "0 0 1 * * ?")
    public void task(){
            staService.createStatistics(DateUtil.formatDate(DateUtil.addDays(new Date(),-1)));
    }
}
