package top.i7un.springboot.mytest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.i7un.springboot.feign.HelloFeign;

/**
 * Created by Noone on 2020-05-19.
 */
@RestController
@RequestMapping("/test")
public class MyTest {

    @Autowired
    private HelloFeign feignClient;

    @RequestMapping("/myTest")
    public String TestMine(String name){
        return feignClient.testMyFeign(name);
    }
}
