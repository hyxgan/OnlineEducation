package com.atguigu.eduService.client;

import com.atguigu.commonutils.R;
import org.springframework.stereotype.Component;

import javax.xml.bind.SchemaOutputResolver;
import java.util.List;

/**
 * @auther hyx
 */
@Component
public class VodFileDegradeFeignClient implements VodClient{
    @Override
    public R deleteVod(String vid) {
        System.out.println("删除视频失败");
        return R.error().message("删除视频失败");
    }

    @Override
    public R BatchdeleteVideo(List<String> videoList) {
        System.out.println("批量删除视频失败");
        return R.error().message("批量删除视频失败了");
    }
}
