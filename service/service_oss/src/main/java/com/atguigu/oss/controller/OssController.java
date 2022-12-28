package com.atguigu.oss.controller;

import com.atguigu.commonutils.R;
import com.atguigu.oss.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @auther hyx
 */
@RestController
@RequestMapping("/eduoss/fileoss")
//@CrossOrigin
public class OssController {
      @Autowired
    FileService fileService;

      @PostMapping()
      public R uploadOssFile(MultipartFile file){
//          获取上传文件
          String url = fileService.FileUpload(file);

          return R.ok().data("url",url);
      }

}
