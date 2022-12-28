package com.atguigu.eduService.controller;


import com.alibaba.excel.util.StringUtils;
import com.atguigu.eduService.client.VodClient;
import com.atguigu.commonutils.R;
import com.atguigu.eduService.entity.EduVideo;
import com.atguigu.eduService.service.EduVideoService;
import com.atguigu.exception.GuliException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-11-25
 */
@RestController
@RequestMapping("/eduService/edu-video")
//@CrossOrigin
public class EduVideoController {
        @Autowired
    EduVideoService eduVideoService;

        @Autowired
    private VodClient vodClient;

//        添加小节
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo){
        boolean save = eduVideoService.save(eduVideo);
        if (save){
            return R.ok();
        }
        else{
            return R.error();
        }

    }

//    删除小节
     @DeleteMapping("/deleteVideo/{videoId}")
     public R deleteVideo(@PathVariable("videoId") String videoId){
        //根据video表的ID获取对应对象
         EduVideo eduVideo = eduVideoService.getById(videoId);
//        根据对象获取视频的ID。
         String videoSourceId = eduVideo.getVideoSourceId();
         if (!StringUtils.isEmpty(videoSourceId)){
             //         根据视频ID删除阿里存的视频
             R result = vodClient.deleteVod(videoSourceId);

             if (result.getCode() == 20001){
                    throw new GuliException(20001,"删除视频失败，熔断器.....");
             }
         }

         boolean b = eduVideoService.removeById(videoId);
         if (b){
             return R.ok();
         }
         else{
             return R.error();
         }
     }

//     修改章节
    @PutMapping("/updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo){
        boolean b = eduVideoService.updateById(eduVideo);
        if (b){
            return R.ok();
        }
        else{
            return R.error();
        }
    }

    //    根据ID查询小节
    @GetMapping("getVideoById/{videoId}")
    public R getVideoById(@PathVariable("videoId") String videoId){
        EduVideo eduVideo = eduVideoService.getById(videoId);
        return R.ok().data("video",eduVideo);
    }
}

