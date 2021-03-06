package top.i7un.springboot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import top.i7un.springboot.aspect.PrincipalAspect;
import top.i7un.springboot.constant.CodeType;
import top.i7un.springboot.model.User;
import top.i7un.springboot.redis.StringRedisServiceImpl;
import top.i7un.springboot.service.MailService;
import top.i7un.springboot.service.UserService;
import top.i7un.springboot.utils.DataMap;
import top.i7un.springboot.utils.JsonResult;
import top.i7un.springboot.utils.MD5Util;

import javax.servlet.http.HttpServletRequest;

/**
 * @author:  Noone
 * @Date: 2018/6/4 11:48
 * Describe:
 */
@RestController
@Slf4j
public class RegisterControl {

    @Autowired
    UserService userService;
    @Autowired
    StringRedisServiceImpl stringRedisService;
//    @Autowired
//    private KafkaTemplate kafkaTemplate;
    @Autowired
    private MailService mailService;


    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String register(User user,
                            HttpServletRequest request){
        try {
            String authCode = request.getParameter("authCode");

            String trueMsgCode = (String) stringRedisService.get(user.getPhone());

//            判断手机号是否正确
            if(trueMsgCode == null){
                return JsonResult.fail(CodeType.PHONE_ERROR).toJSON();
            }
//            判断验证码是否正确
            if(!authCode.equals(trueMsgCode)){
                return JsonResult.fail(CodeType.AUTH_CODE_ERROR).toJSON();
            }
            //判断用户名是否存在
            if(userService.usernameIsExist(user.getUsername()) || user.getUsername().equals(PrincipalAspect.ANONYMOUS_USER)){
                return JsonResult.fail(CodeType.USERNAME_EXIST).toJSON();
            }
            //注册时对密码进行MD5加密
            MD5Util md5Util = new MD5Util();
            user.setPassword(md5Util.encode(user.getPassword()));

            //注册结果
            DataMap data = userService.insert(user);
            if (0 == data.getCode()){
                //注册成功删除redis中的验证码
                stringRedisService.remove(user.getPhone());
            }
            try {
//                kafkaTemplate.send("register_topic", JSONObject.toJSONString(user));
                mailService.sendRegisterMail(user);
            } catch (Exception e) {
                log.warn("{}注册成功但是没有发送邮件",user.getUsername(),e);
            }
            return JsonResult.build(data).toJSON();
        } catch (Exception e){
            log.error("User [{}] register exception", user, e);
        }
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }



}
