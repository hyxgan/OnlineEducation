package com.atguigu.eduorder.service;

import com.atguigu.eduorder.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-12-14
 */
public interface OrderService extends IService<Order> {

    String createOrder(String courseId,String memberId);

    Order getByOrderId(String oid);

    Boolean isOrNoBuyCourse(String courseId, String memberId);
}
