package top.i7un.springboot.mytest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Noone on 2020-08-31.
 */
@Service
public class ClazzB {

    @Autowired
    private ClazzA clazzA;

    public void test1(){
        System.out.println("bbb");
    }

    public void testMy(){
        clazzA.test1();
    }
}
