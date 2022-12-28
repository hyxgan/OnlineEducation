package com.atguigu.eduorder.client;

import com.atguigu.commonutils.ordervo.UcenterMemberOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @auther hyx
 */
@Component
@FeignClient("service-ucenter")
public interface UcenterClient {

    @PostMapping("/eduucenter/ucenter/getMemberInfoOrder/{memberId}")
    public com.atguigu.commonutils.ordervo.UcenterMemberOrder getMemberInfoOrder(@PathVariable("memberId") String memberId);
}
