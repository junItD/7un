package top.i7un.springboot.aspect.annotation;

import java.lang.annotation.*;

/**
 *
 * @author Noone
 * @date 2020/7/2
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
//@Inherited
//@Documented
public @interface MyTestAnnotation {

    String value();

    String x() default "1";
}
