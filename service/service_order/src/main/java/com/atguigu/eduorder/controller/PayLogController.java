package com.atguigu.eduorder.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduorder.service.OrderService;
import com.atguigu.eduorder.service.PayLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-12-14
 */
@RestController
@RequestMapping("/eduorder/paylog")
//@CrossOrigin
public class PayLogController {
    @Autowired
    PayLogService payLogService;
//    生成微信支付二维码
        @GetMapping("createNative/{orderId}")
        public R createNative(@PathVariable("orderId") String orderId){
                Map map = payLogService.createNativeByOid(orderId);
            System.out.println(map);
             return R.ok().data(map);
        }

//        判断订单支付状态
    @GetMapping("queryOrderStatus/{orderNo}")
    public R queryOrderStatus(@PathVariable String orderNo){
            Map<String,String> map = payLogService.getOrderStatus(orderNo);
            if (map == null){
                return R.error().message("订单支付失败");
            }
            if (map.get("trade_state").equals("SUCCESS")){  //支付成功
//                修改订单表的status字段为1，然后在支付记录表中加一条记录。
                 payLogService.updateOrderStatus(map);
                return R.ok().message("支付成功");
            }
            return R.ok().code(25000).message("支付中");
        }
}

