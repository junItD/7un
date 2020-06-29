package top.i7un.springboot.service;

import com.alibaba.fastjson.JSONObject;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import top.i7un.springboot.model.User;

/**
 * Created by Noone on 2020/6/28.
 */
@Component
public class KafkaConsumerService {

    @Autowired
    private MailService mailService;

    @KafkaListener(topics = {"register_topic"})
    public void sendMailByKafka(String userStr){
        User user = JSONObject.parseObject(userStr, User.class);
        String text = "公司："+user.getCompany()+"\r\n手机号："+user.getPhone()+"\r\n 用户名："+user.getUsername()+"\r\n 性别"+user.getGender();
        mailService.sendMail("347436604@qq.com","有人注册了",text);
    }

    @KafkaListener(topics = {"leaveMessage_topic"})
    public void sendMailLeaveMessage(ConsumerRecord<?,?> record){
        String text = "访客<"+record.key().toString()+">给你留言\r\n"+record.value();
        mailService.sendMail("347436604@qq.com","有人留言了",text);
    }
}
