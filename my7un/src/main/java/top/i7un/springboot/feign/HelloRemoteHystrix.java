package top.i7un.springboot.feign;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Noone on 2020/6/1.
 */
@Component
public class HelloRemoteHystrix implements HelloFeign{
    @Override
    public String testMyFeign(@RequestParam(value = "name") String name) {
        return "i'm error!!";
    }
}
