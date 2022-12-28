package com.atguigu.edustatistic.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @auther hyx
 */
@Component
@FeignClient("service-ucenter")
public interface UcenterClient {
    @GetMapping("/eduucenter/ucenter/registerCount/{date}")
    public Integer registerCount(@PathVariable("date") String date);
}
