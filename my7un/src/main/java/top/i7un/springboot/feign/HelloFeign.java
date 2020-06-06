package top.i7un.springboot.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Noone on 2020/5/29.
 */
//@FeignClient(name = "FEIGN-SERVER", fallback = HelloRemoteHystrix.class)
public interface HelloFeign {

    @RequestMapping("/testMyFeign/hello")
     String testMyFeign(@RequestParam String name);
}
