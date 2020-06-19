package top.i7un.springboot.mytest;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.i7un.springboot.feign.HelloFeign;
import top.i7un.springboot.mapper.WorkMapper;
import top.i7un.springboot.model.Work;

/**
 * Created by Noone on 2020-05-19.
 */
@RestController
@RequestMapping("/test")
public class MyTest {

    @Autowired
    private HelloFeign feignClient;

    @Autowired
    private WorkMapper workMapper;
    @RequestMapping("/myTest")
    public String TestMine(String name){
        return feignClient.testMyFeign(name);
    }

    @RequestMapping("/123")
    public String test1(){
        int i = 1/0;
        return "123";
    }

    @RequestMapping("/two")
    public String testTwo(){
        Work twoParams = workMapper.getTwoParams("开发工程师", "技术部","123");
        System.out.println(twoParams);
        return JSONObject.toJSONString(twoParams);
    }
}
