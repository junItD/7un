package top.i7un.springboot.mytest;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Created by Noone on 2020/6/28.
 */
@Component
public class KafkaLisener {

    @KafkaListener(topics = "my_test_topic")
    public void lesten(ConsumerRecord<?,?> record){
        System.out.println(111);
        System.out.println(record.topic()+record.key()+record.value());
        System.out.println(222);
    }

    @KafkaListener(topics = "my_test_topic")
    public void lesten2(ConsumerRecord<?,?> record){
        System.out.println(333);
    }
}
