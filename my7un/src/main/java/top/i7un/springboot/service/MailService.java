package top.i7un.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import top.i7un.springboot.model.User;

/**
 * Created by Noone on 2020/6/28.
 */
@Component
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String from;

    public void sendMail(String to,String subject,String text){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }

    @Async
    public void sendRegisterMail(User user) {
        String text = "公司："+user.getCompany()+"\r\n手机号："+user.getPhone()+"\r\n 用户名："+user.getUsername()+"\r\n 性别"+user.getGender();
        sendMail("347436604@qq.com","有人注册了",text);
    }

    @Async
    public void sendMessageMail(String leaveMessageContent, String answerer) {
        String text = "访客<"+answerer+">给你留言\r\n"+leaveMessageContent;
        sendMail("347436604@qq.com","有人留言了",text);
    }

}
