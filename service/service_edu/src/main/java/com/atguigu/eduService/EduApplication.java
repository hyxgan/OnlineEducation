package com.atguigu.eduService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 * @auther hyx
 */
@ComponentScan(basePackages = {"com.atguigu"})
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class EduApplication {
    public static void main(String[] args) {
          SpringApplication.run(EduApplication.class,args);
    }
}
