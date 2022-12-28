package com.atguigu.eduCms.controller;

import com.atguigu.commonutils.R;
import com.atguigu.eduCms.entity.CrmBanner;
import com.atguigu.eduCms.service.CrmBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @auther hyx
 * 前台显示
 */
@RestController
//@CrossOrigin
@RequestMapping("/eduCms/bannerFront")
public class BannerFrontController {
        @Autowired
    CrmBannerService crmBannerService;

//        查询所有幻灯片
    @GetMapping("getAllBanner")
    public R getAllBanner(){
        List<CrmBanner> banners = crmBannerService.selectAllBanner();
        return R.ok().data("banners",banners);
    }
}
