package com.atguigu.eduorder.service.impl;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.ordervo.EduCourseOrder;
import com.atguigu.commonutils.ordervo.UcenterMemberOrder;
import com.atguigu.eduorder.client.EduClient;
import com.atguigu.eduorder.client.UcenterClient;
import com.atguigu.eduorder.entity.Order;
import com.atguigu.eduorder.mapper.OrderMapper;
import com.atguigu.eduorder.service.OrderService;
import com.atguigu.eduorder.util.OrderNoUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-12-14
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    @Autowired
    EduClient eduClient;

    @Autowired
    UcenterClient ucenterClient;
    @Override
    public String createOrder(String courseId, String memberId) {
//         获取课程信息
        EduCourseOrder courseInfoOrder = eduClient.getCourseInfoOrder(courseId);

//        获取用户信息
        UcenterMemberOrder memberInfoOrder = ucenterClient.getMemberInfoOrder(memberId);

//        创建订单类
        Order order = new Order();
//        将用户信息和课程详细信息加入到order中，然后存入数据库

        order.setOrderNo(OrderNoUtil.getOrderNo());
        order.setCourseId(courseId);
        order.setCourseTitle(courseInfoOrder.getTitle());
        order.setCourseCover(courseInfoOrder.getCover());
        order.setTeacherName(courseInfoOrder.getTeacherName());
        order.setTotalFee(courseInfoOrder.getPrice());
        order.setMemberId(memberId);
        order.setMobile(memberInfoOrder.getMobile());
        order.setNickname(memberInfoOrder.getNickname());
        order.setStatus(0);
        order.setPayType(1);
        baseMapper.insert(order);

        return order.getOrderNo();
    }

    @Override
    public Order getByOrderId(String oid) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",oid);

        Order order = baseMapper.selectOne(wrapper);

        return order;

    }

    @Override
    public Boolean isOrNoBuyCourse(String courseId, String memberId) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        wrapper.eq("member_id",memberId);
        wrapper.eq("status",1);
        Integer count = baseMapper.selectCount(wrapper);
        if (count > 0){
                 return true;
        }
        else{
            return false;
        }
    }
}
