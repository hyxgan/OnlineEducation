package com.atguigu.eduucenter.service;

import com.atguigu.eduucenter.entity.UcenterMember;
import com.atguigu.eduucenter.entity.vo.RegisterVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-12-04
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    String verify(UcenterMember member);

    void register(RegisterVo registerVo);

    UcenterMember getOpenIdMember(String openid);

    Integer getRegisterCount(String date);
}
