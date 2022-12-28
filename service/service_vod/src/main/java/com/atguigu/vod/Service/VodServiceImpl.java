package com.atguigu.vod.Service;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.atguigu.commonutils.R;
import com.atguigu.exception.GuliException;
import com.atguigu.vod.utils.ConstantPropertiesUtils;
import com.atguigu.vod.utils.InitObject;
import io.netty.util.Constant;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

/**
 * @auther hyx
 */
@Component
public class VodServiceImpl implements VodService{

    public String uploadVideo(MultipartFile file) {
        try{
            String fileName = file.getOriginalFilename();   //上传文件本来的名称

            String title = fileName.substring(0, fileName.lastIndexOf("."));  //上传后的文件名

            InputStream inputStream = file.getInputStream();


            UploadStreamRequest request = new UploadStreamRequest(ConstantPropertiesUtils.ACCESS_KEY_ID,ConstantPropertiesUtils.ACCESS_KEY_SECRET, title, fileName, inputStream);

            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);

            String VideoId;
            if (response.isSuccess()) {
                VideoId = response.getVideoId();
            } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                VideoId = response.getVideoId();
            }
            return VideoId;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

      @Override
    public void deleteVod(String vid) {
          try{
              //初始化对象
              DefaultAcsClient client = InitObject.initVodClient(ConstantPropertiesUtils.ACCESS_KEY_ID, ConstantPropertiesUtils.ACCESS_KEY_SECRET);
            //        创建删除视频request对象
              DeleteVideoRequest request = new DeleteVideoRequest();
              request.setVideoIds(vid);
            //        调用初始化对象的方法实现删除
              DeleteVideoResponse response = client.getAcsResponse(request);

          }catch (Exception e){
              e.printStackTrace();
              throw new GuliException(20001,"删除视频失败");
          }

    }

    @Override
    public void deleteBatchVod(List<String> videoList) {
        try {
//            批量删除视频时，ID必须是以“，”隔开的字符串。
            String videolist = StringUtils.join(videoList, ",");

            DefaultAcsClient client = InitObject.initVodClient(ConstantPropertiesUtils.ACCESS_KEY_ID, ConstantPropertiesUtils.ACCESS_KEY_SECRET);

            DeleteVideoRequest request = new DeleteVideoRequest();

            request.setVideoIds(videolist);

            client.getAcsResponse(request);

        }catch (Exception e){
            e.printStackTrace();
            throw new GuliException(20001,"删除失败");
        }


    }
}
