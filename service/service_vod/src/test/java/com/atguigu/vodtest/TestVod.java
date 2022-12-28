package com.atguigu.vodtest;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.atguigu.vod.utils.InitObject;
import org.junit.Test;

import java.util.List;

/**
 * @auther hyx
 */
public class TestVod {
//    本地上传
   /* public static void main(String[] args) throws ClientException {
        String accessKeyId = "LTAI5tBFpLamBDAmaWiNUE8w";
        String accessKeySecret = "pdsMRGA5D645I71D3SiXzkUDRYoG8m";
        String title = "What If I Want to Move Faster.mp4";  //文件上传后的文件名
        String fileName = "E:\\What If I Want to Move Faster.mp4";  //要上传文件的本地路径

//        上传视频的方法
        UploadVideoRequest request = new UploadVideoRequest(accessKeyId, accessKeySecret, title, fileName);
        *//* 可指定分片上传时每个分片的大小，默认为2M字节 *//*
        request.setPartSize(2 * 1024 * 1024L);
        *//* 可指定分片上传时的并发线程数，默认为1，(注：该配置会占用服务器CPU资源，需根据服务器情况指定）*//*
        request.setTaskNum(1);

        UploadVideoImpl uploader = new UploadVideoImpl();
//       执行uploadVideo方法进行上传
        UploadVideoResponse response = uploader.uploadVideo(request);

        if (response.isSuccess()) {
            System.out.print("VideoId=" + response.getVideoId() + "\n");
        } else {
            *//* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 *//*
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");


        }
        getPlayAuth();
     }*/

@Test
    //        根据视频ID获取视频播放凭证
     public void getPlayAuth() throws ClientException {

//        创建初始化对象
         DefaultAcsClient client = InitObject.initVodClient("LTAI5tBFpLamBDAmaWiNUE8w", "pdsMRGA5D645I71D3SiXzkUDRYoG8m");

//        创建获取视频凭证的request和response对象
         GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
         GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();

         request.setVideoId("46b1e7986a244e458777dfdf717793bc");

         response = client.getAcsResponse(request);

         System.out.println("playAuth:" + response.getPlayAuth());
     }


   /* //          根据视频ID获取视频地址 ： 如果视频加密，获取地址也播放不了。但是视频凭证可以播放。
    public static void getPlayUrl() throws ClientException {

//        创建初始化对象
        DefaultAcsClient client = InitObject.initVodClient("LTAI5tBFpLamBDAmaWiNUE8w", "pdsMRGA5D645I71D3SiXzkUDRYoG8m");


//        创建获取视频地址的request,response对象
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        GetPlayInfoResponse response = new GetPlayInfoResponse();
//
//
//       向request对象里面设置视频id
        request.setVideoId("721fa3de77eb489c9558f51b7139e044");

//        调用初始化对象里面的方法，传递request，获取数据
        response = client.getAcsResponse(request);

        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();

//播放地址
        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList){
            System.out.println("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
        }

//        Base信息:视频名称
        System.out.println("VideoBase.Title = " +response.getVideoBase().getTitle() + "\n");

    }*/
}
