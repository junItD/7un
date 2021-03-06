package top.i7un.springboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author:  Noone
 * @Date: 2018/6/6 20:03
 * Describe: 定制错误页面
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //配置静态资源映射
        registry.addResourceHandler("/article/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/getWorkRecordById/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/resume/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/index/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/template/**").addResourceLocations("classpath:/static/");
    }

}
