package com.atguigu.msmservice.service;

import java.util.Map;

/**
 * @auther hyx
 */
public interface MsmService {
    boolean sendCode(Map<String, Object> map, String phone);
}
