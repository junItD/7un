package top.i7un.springboot.aspect.annotation;

import java.lang.annotation.*;
import java.util.List;

/**
 * @author:  Noone
 * @Date: 2019/11/1 13:25
 * Describe:
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PermissionCheck {

    String[] value();


}
