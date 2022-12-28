package com.atguigu.oss.service.Impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.atguigu.commonutils.R;
import com.atguigu.oss.service.FileService;
import com.atguigu.oss.utils.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

/**
 * @auther hyx
 */
@Component
public class FileServiceImpl implements FileService {

    @Override
    public String FileUpload(MultipartFile file) {
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        String endPoint = ConstantPropertiesUtils.END_POINT;  //地域节点
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;
            try{
//                创建OSS实例
                OSS ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);
//              获取文件输入流
                InputStream inputStream = file.getInputStream();
//               获取文件原始文件名
                String fileName = file.getOriginalFilename();
//              1. 文件名称里面添加随机唯一值
                String uuid = UUID.randomUUID().toString().replaceAll("-","");
                fileName = uuid + fileName;

//               2.把文件按照日期进行管理
                String datePath = new DateTime().toString("yyyy/MM/dd");

                fileName = datePath +"/"+ fileName;
                /*
               * 第一个参数 ： bucket名称
               * 第二个参数 上传到oss文件路径和文件名称
               * 第三个参数 ： 要上传的文件的输入流
               * */
                ossClient.putObject(bucketName,fileName,inputStream);

//                关闭OssClient
                ossClient.shutdown();

// https://edu-e1.oss-cn-hangzhou.aliyuncs.com/%E6%8B%93%E6%89%91%E5%9B%BE.jpg
//路径= https://  + bucket名称 + 地域节点 + 文件名

                String url = "https://" + bucketName +"." + endPoint + "/" + fileName;
                return url;
            }catch (Exception e){
                e.printStackTrace();
            }

        return null;
    }
}
