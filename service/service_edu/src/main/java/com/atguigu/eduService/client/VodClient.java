package com.atguigu.eduService.client;

import com.atguigu.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @auther hyx
 */
@FeignClient(name="service-vod",fallback = VodFileDegradeFeignClient.class)
@Component
public interface VodClient {
//这里调用要用完全路径
        @DeleteMapping("/eduvod/video/deleteVod/{vid}")
        public R deleteVod(@PathVariable("vid") String vid);

        @DeleteMapping("/eduvod/video/delete-batch")
        public R BatchdeleteVideo(List<String> videoList);
}
