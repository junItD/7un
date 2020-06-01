package top.qun.hystrix.hystrixdemo;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Noone on 2020/6/1.
 */
@FeignClient(value = "FEIGN-SERVER",fallback = HiHystrix.class)
public interface HelloHystrix {

    @RequestMapping("/testMyFeign/hello")
    String testMyHystrix(@RequestParam String name);
}
