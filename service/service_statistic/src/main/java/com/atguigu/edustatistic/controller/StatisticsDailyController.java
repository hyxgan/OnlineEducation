package com.atguigu.edustatistic.controller;


import com.atguigu.commonutils.R;
import com.atguigu.edustatistic.entity.StatisticsDaily;
import com.atguigu.edustatistic.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-12-17
 */
@RestController
@RequestMapping("/edustatistic/daily")
//@CrossOrigin
public class StatisticsDailyController {
    @Autowired
    StatisticsDailyService statisticsDailyService;

    @GetMapping("createStatisticsByDate/{date}")
    public R createStatisticsByDate(@PathVariable String date){
            statisticsDailyService.createStatistics(date);
            return R.ok();
    }

    @GetMapping("getMapInfo/{type}/{start}/{end}")
    public R getMapInfo(@PathVariable("type") String type,
                        @PathVariable("start") String start,
                        @PathVariable("end") String end){
                Map<String,Object> map = statisticsDailyService.getShowData(type,start,end);
                return R.ok().data(map);
    }
}

