package com.atguigu.eduorder.service;

import com.atguigu.eduorder.entity.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-12-14
 */
public interface PayLogService extends IService<PayLog> {

    Map<String, Object> createNativeByOid(String orderId);

    Map<String, String> getOrderStatus(String orderNo);

    void updateOrderStatus(Map<String, String> map);
}
