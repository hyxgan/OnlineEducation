package com.atguigu.eduorder.controller;


import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.eduorder.entity.Order;
import com.atguigu.eduorder.service.OrderService;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-12-14
 */
@RestController
@RequestMapping("/eduorder/order")
//@CrossOrigin
public class OrderController {
        @Autowired
    OrderService orderService;

//        添加订单
        @PostMapping("addOrder/{courseId}")
       public R addOrder(@PathVariable("courseId") String courseId,
                         HttpServletRequest request){
//               获取课程和用户信息
            String memberId = JwtUtils.getMemberIdByJwtToken(request);

           String orderId = orderService.createOrder(courseId,memberId);

         return R.ok().data("orderId",orderId);

        }


//        根据订单ID查询订单信息
     @PostMapping("getOrderInfo/{oid}")
     public R getOrderInfo(@PathVariable("oid") String oid){
               Order order = orderService.getByOrderId(oid);
               return R.ok().data("order",order);
     }

//     判断课程是否支付
     @GetMapping("isBuyCourse/{courseId}/{memberId}")
     public Boolean isBuyCourse(@PathVariable("courseId") String courseId,@PathVariable("memberId") String memberId){
         Boolean boo = orderService.isOrNoBuyCourse(courseId, memberId);
         return boo;
     }
}

