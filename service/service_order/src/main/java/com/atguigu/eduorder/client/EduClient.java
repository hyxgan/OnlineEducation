package com.atguigu.eduorder.client;

import com.atguigu.commonutils.ordervo.EduCourseOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @auther hyx
 */
@Component
@FeignClient("service-edu")
public interface EduClient {
    @PostMapping("/eduService/coursefront/getCourseInfoOrder/{courseId}")
    public com.atguigu.commonutils.ordervo.EduCourseOrder getCourseInfoOrder(@PathVariable("courseId") String courseId);
}
