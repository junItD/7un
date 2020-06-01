package top.qun.hystrix.hystrixdemo;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Noone on 2020/6/1.
 */
@Component
public class HiHystrix implements HelloHystrix{
    @Override
    public String testMyHystrix(@RequestParam(value = "name") String name) {
        return "i'm error";
    }
}
