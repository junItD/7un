//package top.i7un.springboot.mytest;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * Created by Noone on 2020/6/28.
// */
//@RequestMapping("/kafka")
//@RestController
//public class KafkaTest {
//
//    @Autowired
//    private KafkaTemplate kafkaTemplate;
//    @RequestMapping("/test")
//    public String testKafka(){
//        kafkaTemplate.send("my_test_topic","1","a");
//        return "ok";
//    }
//}
