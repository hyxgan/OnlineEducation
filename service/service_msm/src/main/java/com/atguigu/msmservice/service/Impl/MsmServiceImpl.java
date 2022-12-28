package com.atguigu.msmservice.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.client.naming.utils.StringUtils;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.atguigu.msmservice.service.MsmService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @auther hyx
 */
@Service
public class MsmServiceImpl implements MsmService {

    @Override
    public boolean sendCode(Map<String, Object> map, String phone) {
        /**
         * 发送短信
         */
            if(StringUtils.isEmpty(phone))
            {
                return false;
            }

            DefaultProfile profile = DefaultProfile.getProfile(
                       "default",
                    "LTAI5tBFpLamBDAmaWiNUE8w",
                       "pdsMRGA5D645I71D3SiXzkUDRYoG8m");



            IAcsClient client = new DefaultAcsClient(profile);

            CommonRequest request = new CommonRequest();
//request.setProtocol(ProtocolType.HTTPS);
            request.setMethod(MethodType.POST);
            request.setDomain("dysmsapi.aliyuncs.com");
            request.setVersion("2017-05-25");
            request.setAction("SendSms");

            request.putQueryParameter("PhoneNumbers", phone);  //手机号
            request.putQueryParameter("SignName", "明天在线教育网站");    //申请签名的签名名称
            request.putQueryParameter("TemplateCode", "SMS_262570452");    //模板code
            request.putQueryParameter("TemplateParam", JSONObject.toJSONString(map));   //验证码数据，要求是json数据传递


        CommonResponse response = null;
        try {
            response = client.getCommonResponse(request);
            System.out.println(response.getData());
            boolean success = response.getHttpResponse().isSuccess();
            return success;

        } catch (ClientException e) {
            e.printStackTrace();
        }

            return false;
        }
    }

