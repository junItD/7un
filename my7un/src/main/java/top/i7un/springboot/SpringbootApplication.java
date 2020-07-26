package top.i7un.springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import top.i7un.springboot.mytest.SpringBeanPojo;
import top.i7un.springboot.mytest.Start;

@SpringBootApplication
@EnableTransactionManagement
@EnableWebMvc
@EnableScheduling
@EnableAsync
//@EnableEurekaClient
//@EnableFeignClients
@MapperScan("top.i7un.springboot.mapper")
public class SpringbootApplication {

    @Bean(initMethod = "springPostConstruct",destroyMethod = "myDestroy")
    public SpringBeanPojo xxx(){
        SpringBeanPojo springBeanPojo = new SpringBeanPojo();
        springBeanPojo.setName("my name is 123");
        return springBeanPojo;
    }
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(SpringbootApplication.class, args);
        SpringBeanPojo springbeanpojo = (SpringBeanPojo) run.getBean("top.i7un.springboot.mytest.SpringBeanPojo");
        System.out.println(springbeanpojo.getName());

//        javaconfig的斑斑
//        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(Start.class);
//        String[] beanDefinitionNames = annotationConfigApplicationContext.getBeanDefinitionNames();
//        for (String beanDefinitionName : beanDefinitionNames) {
//            System.out.println(beanDefinitionName);
//        }
//            xml的版本
//        ClassPathXml ApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("xxx.xml");


    }

}
