package top.i7un.springboot.mytest;

import lombok.Data;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Created by Noone on 2020/7/20.
 */
public class SpringBeanPojo implements BeanNameAware, BeanFactoryAware , ApplicationContextAware, InitializingBean, DisposableBean {

    //第一步  bean 实例化
    public SpringBeanPojo() {
        System.out.println("Book Initializing ");
    }
    //第二步  bean属性的注入
    public void setName(String name){
        this.name = name;
        System.out.println("set name : name has set");
    }
    //第三步 调用BeanNameAware的setBeanName 方法
    @Override //BeanNameAware 里面的
    public void setBeanName(String s) {
        System.out.println("Book.setBeanName invoke");
    }
    //第四步 调用BeanFactoryAware里面的setBeanFactory方法
    @Override //BeanFactoryAware 里面的
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("Book.setBeanFactory invoke");
    }
    //第五步 调用ApplicationContextAware里面的setApplicationContext
    @Override  //ApplicationContextAware 里面的
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("Book.setApplicationContext invoke");
    }
    //第六步 执行MyBeanPostProcessor.postProcessBeforeInitialization

    //第七步 执行InitializingBean 里面的afterPropertiesSet
    @Override //InitializingBean 初始化
    public void afterPropertiesSet() throws Exception {
        System.out.println("Book.afterPropertiesSet invoke");
    }

    //第八步 调用自定义的初始化方法 但是需要在 @bean中添加 @Bean(initMethod = "springPostConstruct")
    //自定义初始化方法
    public void springPostConstruct(){
        System.out.println("springPostConstruct");
    }


    //第九步 调用MyBeanPostProcessor.postProcessAfterInitialization


    // 第十步 使用bean 比如我调用了getName
    public String getName(){
        return name;
    }

    //第十一步 调用DisposableBean 里面的destroy方法
    @Override //DisposableBean 里面的
    public void destroy() throws Exception {
        System.out.println("DisposableBean lide destory");
    }

    //第十二步 调用自定义的destroy方法
    public void myDestroy() throws Exception {
        System.out.println("Book.destory invoke");
    }

    //第十三步 完成
    @Override
    public void finalize() throws Throwable{
        System.out.println("finalize");
    }
    private String name;
        /*
        Book Initializing
        set name : name has set
        Book.setBeanName invoke
        Book.setBeanFactory invoke
        Book.setApplicationContext invoke
        MyBeanPostProcessor.postProcessBeforeInitialization
        Book.afterPropertiesSet invoke
        springPostConstruct
        MyBeanPostProcessor.postProcessAfterInitialization
        my name is 123
        DisposableBean lide destory
        Book.destory invoke
        finalize
    * */
}
