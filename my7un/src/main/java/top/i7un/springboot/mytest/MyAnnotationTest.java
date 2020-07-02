package top.i7un.springboot.mytest;

import top.i7un.springboot.aspect.annotation.MyTestAnnotation;

import java.lang.reflect.Method;

/**
 * Created by Noone on 2020/7/2.
 */
public class MyAnnotationTest {

    public static void main(String[] args) {
        String s = testMyAnn();
        Class<MyAnnotationTest> myAnnotationTestClass = MyAnnotationTest.class;
        Method[] methods = myAnnotationTestClass.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(MyTestAnnotation.class)){
                MyTestAnnotation annotation = method.getAnnotation(MyTestAnnotation.class);
                System.out.println(method.getName()+annotation.value()+annotation.x());
            }
        }
        System.out.println("okle");
    }
    @MyTestAnnotation("123")
    private static String testMyAnn(){
        return "1";
    }
}
