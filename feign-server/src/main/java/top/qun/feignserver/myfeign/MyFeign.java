package top.qun.feignserver.myfeign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Noone on 2020/5/29.
 */
@RestController
@RequestMapping("/testMyFeign")
public class MyFeign {
//    @Value("${server.port}")
//    String port;
    @Autowired
    Environment environment;

    public String getPort(){
        return environment.getProperty("local.server.port");
    }
    @RequestMapping("/hello")
    public String test(String name){
        return getPort()+"hello"+name;
    }
}
