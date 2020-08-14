package top.i7un.springboot.mytest;

import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

/**
 * Created by Noone on 2020-07-26.
 */

//@Component
@Import(MyImportSelector.class)
public class Start {

}
