package com.atguigu.oss.service;

import com.atguigu.commonutils.R;
import org.springframework.web.multipart.MultipartFile;

/**
 * @auther hyx
 */
public interface FileService {
    public String FileUpload(MultipartFile file);
}
