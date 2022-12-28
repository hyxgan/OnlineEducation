package com.atguigu.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.atguigu.commonutils.R;
import com.atguigu.vod.Service.VodService;
import com.atguigu.vod.utils.ConstantPropertiesUtils;
import com.atguigu.vod.utils.InitObject;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @auther hyx
 */
@RestController
//@CrossOrigin
@RequestMapping("/eduvod/video")
public class VodController {

    @Autowired
    VodService vodService;

    @PostMapping("uploadVod")
    public R uploadVideo(MultipartFile file){
        String videoId = vodService.uploadVideo(file);
        return R.ok().data("vid",videoId);
    }

    @DeleteMapping("deleteVod/{vid}")
    public R deleteVod(@PathVariable("vid") String vid){
            vodService.deleteVod(vid);
            return R.ok();
    }

    @DeleteMapping("/delete-batch")
    public R BatchdeleteVideo(@RequestBody List<String> videoList){
          vodService.deleteBatchVod(videoList);
          return R.ok();
    }

//    根据视频ID获取视频凭证
    @GetMapping("getPlayAuth/{vid}")
    public R getPlayAuth(@PathVariable("vid") String vid){

//        创建初始化对象
            DefaultAcsClient client = InitObject.initVodClient(ConstantPropertiesUtils.ACCESS_KEY_ID, ConstantPropertiesUtils.ACCESS_KEY_SECRET);

//        创建获取视频凭证的request和response对象
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();

            request.setVideoId(vid);

            try {
                response = client.getAcsResponse(request);
            } catch (ClientException e) {
                e.printStackTrace();
            }

            return R.ok().data("playAuth",response.getPlayAuth());

    }
}
