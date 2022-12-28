package com.atguigu.eduService.service.impl;

import com.alibaba.excel.util.StringUtils;
import com.atguigu.eduService.client.VodClient;
import com.atguigu.eduService.entity.EduVideo;
import com.atguigu.eduService.mapper.EduVideoMapper;
import com.atguigu.eduService.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-11-25
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {
        @Autowired
    VodClient vodClient;
    @Override
    public void removeVideoByCourseId(String courseId) {
        QueryWrapper<EduVideo> wrappervod = new QueryWrapper<>();
        wrappervod.eq("course_id",courseId);
        wrappervod.select("video_source_id");
//        查找sourceID的集合
        List<EduVideo> vsid = baseMapper.selectList(wrappervod);

        List<String> videoIds = new ArrayList<>();
        for ( EduVideo e :vsid) {
            if(e!= null){
                String videoSourceId = e.getVideoSourceId();
                if (!StringUtils.isEmpty(videoSourceId)){
                    videoIds.add(videoSourceId);
                }
            }

        }
        if (videoIds.size() > 0){
            vodClient.BatchdeleteVideo(videoIds);
        }
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);

        baseMapper.delete(wrapper);

    }
}
