package com.atguigu.vod.Service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @auther hyx
 */
public interface VodService {
    String uploadVideo(MultipartFile file);

    void deleteVod(String vid);

    void deleteBatchVod(List<String> videoList);
}
