package top.i7un.springboot.controller;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import top.i7un.springboot.component.PhoneRandomBuilder;
import top.i7un.springboot.redis.StringRedisServiceImpl;
import top.i7un.springboot.utils.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author:  Noone
 * @Date: 2018/6/4 15:03
 * Describe: 注册获得手机验证码
 */
@RestController
@Slf4j
public class GetPhoneCodeControl {

    @Autowired
    StringRedisServiceImpl stringRedisService;

    private static final String REGISTER = "register";
    public static final String RESISTERTEMPLATE ="SMS_193230965";
    public static final String DOMAIN = "dysmsapi.aliyuncs.com";
    /**
     * 阿里云 accessKeyId
     */
    @Value("${aliyun.accessKeyId}")
    private String accessKeyId;

    /**
     * 阿里云 secret
     */
    @Value("${aliyun.secret}")
    private String secret;

    /**
     * 阿里云短信发送模板
     */
    private static final String SIGN_NAME = "峻仔ocean";

    @PostMapping(value = "/getCode", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getAuthCode(@RequestParam("phone") String phone,
                              @RequestParam("sign") String sign){

        String trueMsgCode = PhoneRandomBuilder.randomBuilder();

       //在redis中保存手机号验证码并设置过期时间
        stringRedisService.set(phone, trueMsgCode);
        stringRedisService.expire(phone, 300);
        log.warn("即将给{}发送的验证码为{}",phone,trueMsgCode);
        try {
            sendSmsMsg(phone, trueMsgCode, RESISTERTEMPLATE);
        } catch (Exception e) {
            log.error("[{}] send phone message exception", phone, e);
            return JsonResult.fail().toJSON();
        }
        log.warn("给{}发送的验证码成功 验证码为{}",phone,trueMsgCode);
        return JsonResult.success().toJSON();
    }

    private void sendSmsResponse(String phoneNumber, String code, String msgCode) throws ClientException {

        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        //"***"分别填写自己的AccessKey ID和Secret
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, secret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", "Dysmsapi", "dysmsapi.aliyuncs.com");
        IAcsClient acsClient = new DefaultAcsClient(profile);
        SendSmsRequest request = new SendSmsRequest();
        //填写接收方的手机号码
        request.setPhoneNumbers(phoneNumber);
        //此处填写已申请的短信签名
        request.setSignName(SIGN_NAME);
        //此处填写获得的短信模版CODE
        request.setTemplateCode(msgCode);
        //笔者的短信模版中有${code}, 因此此处对应填写验证码
        request.setTemplateParam("{\"code\":\"" + code + "\"}");
        acsClient.getAcsResponse(request);

    }

    private void sendSmsMsg(String phoneNumber, String code, String msgCode){
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, secret);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain(DOMAIN);
        String version = "2017-05-25";
        request.setSysVersion(version);
        String sendSms = "SendSms";
        request.setSysAction(sendSms);
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phoneNumber);
        request.putQueryParameter("SignName", "i夋夋爬上云端");
        request.putQueryParameter("TemplateCode", RESISTERTEMPLATE);
        request.putQueryParameter("TemplateParam", "{\"code\":\"" + code + "\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (Exception e) {
           log.warn("发送短信异常",e);
        }
    }

}
