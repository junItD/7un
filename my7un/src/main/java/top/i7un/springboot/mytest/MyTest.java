package top.i7un.springboot.mytest;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import top.i7un.springboot.feign.HelloFeign;
import top.i7un.springboot.mapper.WorkMapper;
import top.i7un.springboot.model.Work;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Noone on 2020-05-19.
 */
@RestController
@RequestMapping("/test")
public class MyTest {

    @Bean
    public BookBo bookBo(){
        return new BookBo("1","2");
    }
    @Autowired
    private HelloFeign feignClient;
    @Autowired
    private BookBo bookBo;

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private WorkMapper workMapper;
    @RequestMapping("/myTest")
    public String TestMine(String name){
        return feignClient.testMyFeign(name);
    }

    @RequestMapping("/123")
    public String test1(){
        return bookBo.getName()+bookBo.getAuthor();
//        int i = 1/0;
//        return "123";
    }

    @RequestMapping("/two")
    public String testTwo(){
        Work twoParams = workMapper.getTwoParams("开发工程师", "技术部","123");
        System.out.println(twoParams);
        return JSONObject.toJSONString(twoParams);
    }

    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(i);
        }
        List<Integer> collect = list.stream().filter(integer -> integer % 2 == 0).collect(Collectors.toList());
        ArrayList<Integer> list1 = new ArrayList<>();
        list.stream().filter(integer -> integer % 2 == 0).forEach(integer -> {
            list1.add(integer-1);
        });
        List<Integer> collect1 = list.stream().filter(integer -> integer % 2 == 0).map(integer -> integer - 1).collect(Collectors.toList());
        System.out.println(collect);
        System.out.println(list1);
        System.out.println(collect1);


    }

}
