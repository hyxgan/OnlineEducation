package com.atguigu.eduucenter.controller;


import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.commonutils.ordervo.UcenterMemberOrder;
import com.atguigu.eduucenter.entity.UcenterMember;
import com.atguigu.eduucenter.entity.vo.RegisterVo;
import com.atguigu.eduucenter.service.UcenterMemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-12-04
 */
@RestController
@RequestMapping("/eduucenter/ucenter")
//@CrossOrigin
public class UcenterMemberController {
    @Autowired
    UcenterMemberService ucenterMemberService;

    @PostMapping("login")
    public R login(@RequestBody UcenterMember member){
        String token = ucenterMemberService.verify(member);
        return R.ok().data("token",token);
    }

    @PostMapping("register")
    public R register(@RequestBody RegisterVo registerVo){
           ucenterMemberService.register(registerVo);
           return R.ok();
    }

//    根据token获取用户信息
    @GetMapping("/getMemberInfo")
    public R getMemberInfo(HttpServletRequest request)
    {   //调用jwt工具类的方法。根据request对象获取头信息，返回用户ID
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
//        查询数据库，根据用户ID获取用户信息
        UcenterMember member = ucenterMemberService.getById(memberId);

        return R.ok().data("userInfo",member);

    }

//  远程调用接口
//    通过用户ID获取用户信息,调用端接受用户信息和被调用端返回用户信息必须是同一个类，不然接受不了
//    所以把类放到common_util模块内。
    @PostMapping("getMemberInfoOrder/{memberId}")
    public UcenterMemberOrder getMemberInfoOrder(@PathVariable("memberId") String memberId){
        UcenterMember member = ucenterMemberService.getById(memberId);
        UcenterMemberOrder ucenterMemberOrder = new UcenterMemberOrder();
        BeanUtils.copyProperties(member,ucenterMemberOrder);
        return ucenterMemberOrder;
    }

    //远程调用接口
    @GetMapping("registerCount/{date}")
    public Integer registerCount(@PathVariable("date") String date){
         Integer count = ucenterMemberService.getRegisterCount(date);
         return count;
    }
}

