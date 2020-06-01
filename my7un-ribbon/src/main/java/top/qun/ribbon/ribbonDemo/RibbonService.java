package top.qun.ribbon.ribbonDemo;

import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Noone on 2020/6/1.
 */
@Service
public class RibbonService {
    @Autowired
    RestTemplate restTemplate;

    public String hiRibbon(String name){
        return restTemplate.getForObject("http://feign-server/testMyFeign/hello?name="+name, String.class);
    }
}
