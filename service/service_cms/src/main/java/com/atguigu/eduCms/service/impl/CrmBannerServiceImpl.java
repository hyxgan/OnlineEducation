package com.atguigu.eduCms.service.impl;

import com.atguigu.eduCms.entity.CrmBanner;
import com.atguigu.eduCms.mapper.CrmBannerMapper;
import com.atguigu.eduCms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-12-03
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    @Override
    @Cacheable( value = "banner",key = "'selectIndexList'")
    public List<CrmBanner> selectAllBanner() {
        List<CrmBanner> Banners = baseMapper.selectList(null);
        return Banners;
    }
}
