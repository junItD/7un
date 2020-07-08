package top.i7un.springboot.mytest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Noone on 2020/7/3.
 */
public class FanXingTest {

    public static void main(String[] args) {

        System.out.println(getInt());
    }

    public static int getInt(){
        try {
            int i = 1/0;
            return 1;
        } catch (Exception e) {
            return 2;
        }finally {
            System.out.println("执行finally");
//            return 3;
        }
    }
}
