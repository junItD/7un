package top.i7un.springboot.mytest;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Noone on 2020/6/29.
 */
public class LamudaTest {

    public static void main(String[] args) {

        BookBo bookBo1 = new BookBo("1", "语文");
        BookBo bookBo2 = new BookBo("2", "数学");
        BookBo bookBo3 = new BookBo("3", "英语");
        BookBo bookBo4 = new BookBo("4", "地理");
        BookBo bookBo5 = new BookBo("5", "生物");
        BookBo bookBo6 = new BookBo("1", "语文");
        List<BookBo> bookBoList = Arrays.asList(bookBo1, bookBo2, bookBo3, bookBo4, bookBo5, bookBo6);

        List<BookBo> distinctList = bookBoList
                //collectingAndThen收集然后在
                .stream().collect(Collectors.collectingAndThen(Collectors.toCollection(()->new TreeSet<>(Comparator.comparing(o-> o.getName()))), ArrayList::new));

        System.out.println(distinctList);
    }
}
