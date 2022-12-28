package com.atguigu.canal;

import com.atguigu.canal.client.CanalClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

/**
 * @auther hyx
 */
@SpringBootApplication
public class canalApplication implements CommandLineRunner {
    @Resource
    CanalClient client;

    public static void main(String[] args) {
        SpringApplication.run(canalApplication.class,args);
    }

    @Override
    public void run(String... args) throws Exception {
        client.run();
    }
}
