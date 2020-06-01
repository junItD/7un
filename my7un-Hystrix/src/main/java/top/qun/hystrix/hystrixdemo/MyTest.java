package top.qun.hystrix.hystrixdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.qun.hystrix.hystrixdemo.HelloHystrix;

@RestController
@RequestMapping("/test")
public class MyTest {

    @Autowired
    private HelloHystrix helloHystrix;

    @RequestMapping("/myHystrix")
    public String TestMine(String name){
        return helloHystrix.testMyHystrix(name);
    }
}
