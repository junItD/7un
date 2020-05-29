package top.qun.feignserver.myfeign;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Noone on 2020/5/29.
 */
@RestController
@RequestMapping("/testMyFeign")
public class MyFeign {

    @RequestMapping("/hello")
    public String test(String name){
        return "hello"+name;
    }
}
