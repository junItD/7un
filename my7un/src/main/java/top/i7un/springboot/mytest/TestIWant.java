package top.i7un.springboot.mytest;

import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import top.i7un.springboot.SpringbootApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Noone on 2020-05-10.
 */
@Controller
public class TestIWant {
//    @GetMapping("/letCpuBusy")
//    public void letCpuBusy(){
//        int i=0;
//        while(true) {
//            i++;
//        }
//
//    }
//    private List<User> userlist=new ArrayList<>();
//
//    //堆区内存溢出
//    @GetMapping("/heapOver")
//    public void heapOverTest(){
//        while(true){
//            userlist.add(new User());
//        }
//    }
public static void main(String[] args) {
//    AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(Start.class);
//    String[] beanDefinitionNames = annotationConfigApplicationContext.getBeanDefinitionNames();
//    for (String beanDefinitionName : beanDefinitionNames) {
//        System.out.println(beanDefinitionName);
//    }

    MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
    map.add("我是峻仔","帅");
    map.add("我是峻仔","聪明");
    map.add("我是询仔","漂亮");
    Set<Map.Entry<String, List<String>>> entries = map.entrySet();
    entries.stream().forEach(stringListEntry -> System.out.println(stringListEntry.getKey()+stringListEntry.getValue()));
}
}
