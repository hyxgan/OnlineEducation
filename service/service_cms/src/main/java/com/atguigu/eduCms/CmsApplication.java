package com.atguigu.eduCms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @auther hyx
 */
@SpringBootApplication
@ComponentScan("com.atguigu")
//如果mapper上没有加@Mapper,一定要加启动类上加@MapperScan
@MapperScan("com.atguigu.eduCms.mapper")
@EnableDiscoveryClient
public class CmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(CmsApplication.class,args);
    }
}
