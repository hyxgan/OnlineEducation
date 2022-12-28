package com.atguigu.eduService.controller;

import com.atguigu.commonutils.R;
import org.springframework.web.bind.annotation.*;

/**
 * @auther hyx
 */
@RestController
@RequestMapping("/eduService/user")
//会出现跨域问题： 要求协议，IP地址，端口号相同。但是前端端口号为9528.后端为8001.不一致。所以出现跨域问题。
//解决方法：在要访问的Controller（接口）上加@CrossOrigin
//@CrossOrigin，利用网关Gateway 访问此接口时，不要加跨域注解。因为网关中已经解决跨域问题了。
public class EduLoginController {
     //login
    @PostMapping("login")
    public R login(){
        return R.ok().data("token","admin");
    }


    //info
    @GetMapping("/info")
    public R info(){
         return R.ok().data("roles","[admin]").data("name","admin").data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }
}
