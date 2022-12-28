package com.atguigu.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @auther hyx
 */
@SpringBootApplication
@EnableDiscoveryClient
public class gatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(gatewayApplication.class,args);
    }
}
