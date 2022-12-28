package com.atguigu.vod.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @auther hyx
 */
//当项目启动后，初始化完成后，执行一个方法afterPropertiesSet
@Component
public class ConstantPropertiesUtils implements InitializingBean {
      @Value("${aliyun.vod.file.keyid}")
      private String keyId;

      @Value("${aliyun.vod.file.keysecret}")
      private String keySecret;

    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECRET;

    @Override
    public void afterPropertiesSet() throws Exception {
        ACCESS_KEY_ID = keyId;
        ACCESS_KEY_SECRET = keySecret;
    }
}
