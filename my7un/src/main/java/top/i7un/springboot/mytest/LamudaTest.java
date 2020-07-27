package top.i7un.springboot.mytest;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Noone on 2020/6/29.
 */
public class LamudaTest {

    @Autowired
    private BookBo bookBo;

    public static void main(String[] args) {

        BookBo bookBo1 = new BookBo("1", "语文");
        BookBo bookBo2 = new BookBo("2", "数学");
        BookBo bookBo3 = new BookBo("3", "英语");
        BookBo bookBo4 = new BookBo("4", "地理");
        BookBo bookBo5 = new BookBo("5", "生物");
        BookBo bookBo6 = new BookBo("1", "语文");
        List<BookBo> bookBoList = Arrays.asList(bookBo1, bookBo2, bookBo3, bookBo4, bookBo5, bookBo6);

//        List<BookBo> distinctList = bookBoList
//                //collectingAndThen收集然后在
//                .stream().collect(Collectors.collectingAndThen(Collectors.toCollection(()->new TreeSet<>(Comparator.comparing(BookBo::getName))), ArrayList::new))
//                .stream().sorted(Comparator.comparing(BookBo::getName)).collect(Collectors.toList());
//        List<BookBo> collect = bookBoList.stream().distinct().collect(Collectors.toList());
//        System.out.println(collect);

        bookBoList.stream().filter(bookBo -> "5".equals(bookBo.getName())).forEach(bookBo -> bookBo.setName("0"));
        System.out.println(bookBoList);
//        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5);
//        Integer reduce = integers.stream().reduce(0, (integer, integer2) -> integer * integer2);
//        System.out.println(reduce);


    }
}
