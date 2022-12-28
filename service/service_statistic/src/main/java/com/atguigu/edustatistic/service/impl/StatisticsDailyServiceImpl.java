package com.atguigu.edustatistic.service.impl;

import com.atguigu.edustatistic.client.UcenterClient;
import com.atguigu.edustatistic.entity.StatisticsDaily;
import com.atguigu.edustatistic.mapper.StatisticsDailyMapper;
import com.atguigu.edustatistic.service.StatisticsDailyService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-12-17
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {
        @Autowired
    UcenterClient ucenterClient;
    @Override
    public void createStatistics(String date) {
        //添加统计记录之前，把当前日期的旧记录删掉，保证数据库中永远是最新数据
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated",date);
        baseMapper.delete(wrapper);

        Integer registerCount = ucenterClient.registerCount(date);

        StatisticsDaily statisticsDaily = new StatisticsDaily();
        statisticsDaily.setDateCalculated(date);
        statisticsDaily.setRegisterNum(registerCount);
        statisticsDaily.setVideoViewNum(RandomUtils.nextInt(100,200));
        statisticsDaily.setCourseNum(RandomUtils.nextInt(100,200));
        statisticsDaily.setLoginNum(RandomUtils.nextInt(100,200));

        baseMapper.insert(statisticsDaily);

    }

    @Override
    public Map<String, Object> getShowData(String type, String start, String end) {
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.between("date_calculated",start,end);
        wrapper.select("date_calculated",type);

        List<StatisticsDaily> dataList = baseMapper.selectList(wrapper);

        List<String> date = new ArrayList<>();
        List<Integer> num = new ArrayList<>();

        for (StatisticsDaily data : dataList){
             date.add(data.getDateCalculated());
             switch (type){
                 case "register_num":
                        num.add(data.getRegisterNum());
                     break;

                 case "login_num":
                        num.add(data.getLoginNum());
                     break;

                 case "video_view_num":
                        num.add(data.getVideoViewNum());
                     break;

                 case "course_num":
                        num.add(data.getCourseNum());
                     break;
             }

        }
        Map<String, Object> map = new HashMap<>();
        map.put("date",date);
        map.put("num",num);

        return map;
    }
}
