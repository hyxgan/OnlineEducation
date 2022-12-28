package com.atguigu.eduucenter.service.impl;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.MD5;
import com.atguigu.eduucenter.entity.UcenterMember;
import com.atguigu.eduucenter.entity.vo.RegisterVo;
import com.atguigu.eduucenter.mapper.UcenterMemberMapper;
import com.atguigu.eduucenter.service.UcenterMemberService;
import com.atguigu.exception.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-12-04
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {
    @Autowired
    RedisTemplate<String,String> redisTemplate;

    @Override
    public String verify(UcenterMember member) {
        String mobile = member.getMobile();
        String password = member.getPassword();

        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)){
                   throw new GuliException(20001,"登录失败");
        }
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);

        UcenterMember mobileMember = baseMapper.selectOne(wrapper);

        if (mobileMember == null){
             throw new GuliException(20001,"登录失败");
        }

        if (!MD5.encrypt(password).equals(mobileMember.getPassword()))
        {
              throw new GuliException(20001,"密码错误,登录失败");
        }

        if (mobileMember.getIsDisabled()){
             throw new GuliException(20001,"账号已被禁用");
        }

//      以上都不成立，登录成功
        String token = JwtUtils.getJwtToken(mobileMember.getId(), mobileMember.getNickname());
        return token;


    }

    @Override
    public void register(RegisterVo registerVo) {
        String mobile = registerVo.getMobile();
        String nickname = registerVo.getNickname();
        String code = registerVo.getCode();
        String password = registerVo.getPassword();

//        判断参数是否为空。
        if (StringUtils.isEmpty(mobile) ||
            StringUtils.isEmpty(mobile) ||
            StringUtils.isEmpty(mobile) ||
            StringUtils.isEmpty(mobile)
           )
        {
                throw new GuliException(20001,"注册失败");
        }

//        判断验证码是否正确
        String verifyCode = redisTemplate.opsForValue().get(mobile);

        if (!code.equals(verifyCode))
        {
             throw new GuliException(20001,"注册失败");
        }

//        判断手机之前是否注册过

        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);

        Integer count = baseMapper.selectCount(wrapper);
        if (count > 0){
            throw new GuliException(20001,"注册失败");
        }

//        注册成功，存到数据库。
        UcenterMember ucenterMember = new UcenterMember();
        ucenterMember.setMobile(mobile);
        ucenterMember.setNickname(nickname);
        ucenterMember.setPassword(MD5.encrypt(password));
        ucenterMember.setIsDisabled(false);
        ucenterMember.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");

        baseMapper.insert(ucenterMember);

    }

    @Override
    public UcenterMember getOpenIdMember(String openid) {
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("openid",openid);
        UcenterMember ucenterMember = baseMapper.selectOne(wrapper);
        return ucenterMember;
    }

    @Override
    public Integer getRegisterCount(String date) {
         return baseMapper.getRegisterCount(date);
    }
}
