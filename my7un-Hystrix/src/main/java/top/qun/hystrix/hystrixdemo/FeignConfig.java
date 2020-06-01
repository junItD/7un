package top.qun.hystrix.hystrixdemo;

import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * Created by Noone on 2020/6/1.
 */
@Configuration
public class FeignConfig {
    @Bean
    public Retryer feignRetryer(){
        return new Retryer.Default(100,TimeUnit.SECONDS.toMillis(1),5);
    }
}
