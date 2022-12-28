package com.atguigu.edustatistic.service;

import com.atguigu.edustatistic.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-12-17
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    void createStatistics(String date);

    Map<String, Object> getShowData(String type, String start, String end);
}
