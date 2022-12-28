package com.atguigu.eduorder.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.commonutils.HttpClient;
import com.atguigu.eduorder.entity.Order;
import com.atguigu.eduorder.entity.PayLog;
import com.atguigu.eduorder.mapper.PayLogMapper;
import com.atguigu.eduorder.service.OrderService;
import com.atguigu.eduorder.service.PayLogService;
import com.atguigu.exception.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.wxpay.sdk.WXPayUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-12-14
 */
@Service
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog> implements PayLogService {
    @Test
    public void test(){
        Map<String, String> map = getOrderStatus("1195693605555834880");
        System.out.println(map);
    }


        @Autowired
    OrderService orderService;

    @Override
    public Map createNativeByOid(String orderId) {
    try{
        //        查询订单信息
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderId);
        Order order = orderService.getOne(wrapper);

//        使用map设置生成二维码所需的参数
        Map m = new HashMap<>();
        m.put("appid", "wx74862e0dfcf69954");
        m.put("mch_id", "1558950191");
        m.put("nonce_str", WXPayUtil.generateNonceStr());
        m.put("body", order.getCourseTitle());
        m.put("out_trade_no", orderId);
        m.put("total_fee", order.getTotalFee().multiply(new BigDecimal("100")).longValue()+"");
        m.put("spbill_create_ip", "127.0.0.1");
        m.put("notify_url","http://guli.shop/api/order/weixinPay/weixinNotify\n");
        m.put("trade_type", "NATIVE");
//        发送httpclient请求，传递参数XML格式，微信支付提供的固定的地址

        com.atguigu.commonutils.HttpClient client =  new com.atguigu.commonutils.HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
//        设置请求所需参数，将集合m转成xml形式。key的作用是加密
        client.setXmlParam(WXPayUtil.generateSignedXml(m,"T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
        client.setHttps(true);
        client.post();
//        得到发送请求返回结果
        String xml = client.getContent();
        Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);

//       返回最终结果的封装
        HashMap map = new HashMap();

        map.put("out_trade_no", orderId);
        map.put("course_id", order.getCourseId());
        map.put("total_fee", order.getTotalFee());
        map.put("result_code", resultMap.get("result_code"));
        map.put("code_url", resultMap.get("code_url"));
        return map;
    }catch (Exception e){
       throw new GuliException(20001,"生成二维码失败");
    }

    }

    @Override
    public  Map<String, String> getOrderStatus(String orderNo) {
        try {
            //1、封装参数
            Map m = new HashMap<>();
            m.put("appid", "wx74862e0dfcf69954");
            m.put("mch_id", "1558950191");
            m.put("out_trade_no", orderNo);
            m.put("nonce_str", WXPayUtil.generateNonceStr());

            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");

            client.setXmlParam(WXPayUtil.generateSignedXml(m,"T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));

            client.setHttps(true);
            client.post();

            String xml = client.getContent();

            Map<String, String> map = WXPayUtil.xmlToMap(xml);
            System.out.println(map);
            return map;
        }catch (Exception e){
           return null;
        }

    }

    @Override
    public void updateOrderStatus(Map<String, String> map) {
        String orderNo = map.get("out_trade_no");
        //        查询订单信息
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderNo);
        Order order = orderService.getOne(wrapper);

        if (order.getStatus().intValue() == 1) {
            return;
        }
//        设置订单表的status为1; 支付为1.未支付为0
        order.setStatus(1);
//        存到数据库
        orderService.updateById(order);

//        向支付记录表中添加一条记录
        PayLog payLog = new PayLog();
        payLog.setOrderNo(orderNo); //订单号
        payLog.setPayTime(new Date());  //支付时间
        payLog.setTotalFee(order.getTotalFee());  //订单费用
        payLog.setTradeState(map.get("trade_state"));  //订单状态
        payLog.setTransactionId(map.get("transaction_id")); //流水号
        payLog.setAttr(JSONObject.toJSONString(map));  //其他信息
        payLog.setPayType(1);//支付类型

        baseMapper.insert(payLog);
    }
}
