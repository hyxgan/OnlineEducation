package com.atguigu.eduucenter.controller;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.eduucenter.entity.UcenterMember;
import com.atguigu.eduucenter.service.UcenterMemberService;
import com.atguigu.eduucenter.util.ConstantPropertiesUtil;
import com.atguigu.eduucenter.util.HttpClientUtils;
import com.atguigu.exception.GuliException;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * @auther hyx
 */
@Controller
@RequestMapping("/eduucenter/wx")
//@CrossOrigin
public class WxApiController {
    @Autowired
    UcenterMemberService ucenterMemberService;

//    扫描二维码后，根据重定向URL访问到callback方法。
    @GetMapping("callback")
     public String callback(String code , String state){
//        访问固定地址，把方法形参code加上。
        String baseAccessTokenUrl =
                "https://api.weixin.qq.com/sns/oauth2/access_token" +
                "?appid=%s" +
                "&secret=%s" +
                "&code=%s" +
                "&grant_type=authorization_code";

        String accessTokenUrl = String.format(
                baseAccessTokenUrl,
                ConstantPropertiesUtil.WX_OPEN_APP_ID,
                ConstantPropertiesUtil.WX_OPEN_APP_SECRET,
                code
        );

        String result = null;
        try {
//            因为要访问一个固定地址，但是不能是自己在浏览器上访问。所以用HttpClient可以代替我们访问地址。
//            返回的是一些参数。
           result = HttpClientUtils.get(accessTokenUrl);
        } catch (Exception e) {
            throw new GuliException(20001,"获取access_token失败");
        }
//我们只要其中的access_token和openId. 所以先将其转成map集合类型。然后获取。
        Gson gson = new Gson();
        Map map = gson.fromJson(result, Map.class);

        String accessToken = (String) map.get("access_token");
        String openid = (String) map.get("openid");
//访问固定地址，参数包括accessToken和openid

        String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                "?access_token=%s" +
                "&openid=%s";
        String userInfoUrl = String.format(
                baseUserInfoUrl,
                accessToken,
                openid
        );
        String userInfo = null;
        try {
//            访问固定地址后返回用户信息
            userInfo = HttpClientUtils.get(userInfoUrl);
        } catch (Exception e) {
            throw new GuliException(20001,"登录失败");
        }

//        将用户信息转成map集合，获取其中的nickname,headimgurl.
        Map userInfoMap = gson.fromJson(userInfo, Map.class);

        String nickname = (String) userInfoMap.get("nickname");
        String headimgurl = (String) userInfoMap.get("headimgurl");

        UcenterMember ucenterMember = ucenterMemberService.getOpenIdMember(openid);

        if (ucenterMember == null){
//            创建登录的实体类，给属性赋值，然后存入数据库。
           ucenterMember = new UcenterMember();
            ucenterMember.setOpenid(openid);
            ucenterMember.setNickname(nickname);
            ucenterMember.setAvatar(headimgurl);

            boolean save = ucenterMemberService.save(ucenterMember);
        }
//        要在首页显示，可以将token放到路径的后面。然后前端在获取token对应的数据。
//        不能将用户信息存在cookie中，然后前端获取。因为cookie不能跨域。

        String token = JwtUtils.getJwtToken(ucenterMember.getId(), nickname);

        return "redirect:http://localhost:3000?token="+token;

    }
    @GetMapping("login")
    public String getWxCode() {
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";

         String url = ConstantPropertiesUtil.WX_OPEN_REDIRECT_URL;
        try {
            url = URLEncoder.encode(url, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String qrcodeUrl = String.format(
                baseUrl,
                ConstantPropertiesUtil.WX_OPEN_APP_ID,
                url,
                "atguigu"
                );
        return "redirect:" + qrcodeUrl;
    }
}
