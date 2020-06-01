package top.qun.ribbon.ribbonDemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Noone on 2020/6/1.
 */
@RestController
@RequestMapping("/testRibbon")
public class RibbonController {

    @Autowired
    RibbonService ribbonService;
    @Autowired
    LoadBalancerClient loadBalancerClient;
    @RequestMapping("/test")
    public String test(String name){
        return ribbonService.hiRibbon(name);
    }
/**查看官方文档可知，负载均衡器LoadBalancerClient是从eureka client获取服务注册列表信息的，并将该信息缓存了一份，在调用choose()方法时，根据负载均衡策略选择一个服务实例的信息，从而维护了负载均衡。LoadBalancerClient可以不从eureka client获取注册列表信息，这时需要自己维护一份信息。*/
/**现在可以知道，在Ribbon中的负载均衡客户端为LoadBalancerClient 。在spring cloud项目中，负载均衡器Ribbon会默认从Eureka Client 的服务注册列表中获取服务的信息，并缓存一份，根据缓存的信息，通过LoadBalancerClient来选择不同的服务实例，从而实现负载均衡，如果禁止Ribbon从Eureka获取注册列表信息，则需要自己去维护一份服务注册列表信息。根据自己维护的信息，Ribbon也可以实现负载均衡。
 */
    @RequestMapping("/testClient")
    public String testClient(String name){
        ServiceInstance instance = loadBalancerClient.choose("feign-server");
        return instance.getHost()+":"+instance.getPort();
    }

}
