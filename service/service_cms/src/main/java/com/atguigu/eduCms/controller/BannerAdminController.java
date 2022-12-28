package com.atguigu.eduCms.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduCms.entity.CrmBanner;
import com.atguigu.eduCms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-12-03
 *
 * 后台接口管理
 */
@RestController
@RequestMapping("/eduCms/bannerAdmin")
//@CrossOrigin
public class BannerAdminController {
      @Autowired
    CrmBannerService crmBannerService;
//  查询
    @GetMapping("pageQuery/{current}/{limit}")
    public R pageQuery(@PathVariable("current") Long current , @PathVariable("limit") Long limit) {
        Page<CrmBanner> page = new Page<CrmBanner>(current,limit);
        crmBannerService.page(page,null);
        return R.ok().data("items",page.getRecords()).data("total",page.getTotal());

    }

//    删除
    @DeleteMapping("/deleteBanner/{id}")
    public R deleteBanner(@PathVariable String id){
        boolean b = crmBannerService.removeById(id);
        if (b){
            return R.ok();
        }
        else{
            return R.error();
        }
    }

//    改
    @PutMapping("updateBanner")
    public R updateBanner(@RequestBody CrmBanner crmBanner){
        boolean b = crmBannerService.updateById(crmBanner);
        if (b){
            return R.ok();
        }
        else{
            return R.error();
        }
    }

// 添加
    @PostMapping("addBanner")
    public R addBanner(@RequestBody CrmBanner crmBanner){
        boolean save = crmBannerService.save(crmBanner);
        if (save){
            return R.ok();
        }
        else{
            return R.error();
        }
    }
}

