package com.atguigu.msmservice.controller;

import com.atguigu.commonutils.R;
import com.atguigu.msmservice.service.MsmService;
import com.atguigu.msmservice.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @auther hyx
 */
@RestController
@RequestMapping("/edumsm/msm")
//@CrossOrigin
public class MsmController {
       @Autowired
    MsmService msmService;
      @Autowired
    RedisTemplate<String,String> redisTemplate;

    @GetMapping("code/{phone}")
    public R sendCode(@PathVariable("phone") String phone){
//        判断手机验证码是否过期，如果没过期就不发送。
        String phonecode = redisTemplate.opsForValue().get("phone");
        if (!StringUtils.isEmpty(phonecode))
        {
             return R.ok();
        }

        String code = RandomUtil.getFourBitRandom();

        Map<String,Object> map = new HashMap<>();
        map.put("code",code);

        boolean b = msmService.sendCode(map, phone);
        if (b){
//            发送验证码成功
//          将验证码存到redis，设置过期时间
//            5是过期时长，后面那个是单位

            redisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);

             return R.ok();
        }
        else{
            return R.error().message("发送短信失败");
        }
    }
}
