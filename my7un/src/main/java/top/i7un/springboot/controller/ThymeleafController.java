package top.i7un.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.Period;

/**
 * Created by Noone on 2020/6/5.
 */
@Controller
public class ThymeleafController {

//    @RequestMapping("/getTimeDiff")
//    public String getTimeDiff(Model model){
//        Period period = Period.between(LocalDate.of(2015, 9, 10), LocalDate.now());
//        String timeDiff = period.getYears() + "年" + period.getMonths() + "月零" + period.getDays() + "天";
////        model.addAttribute("timeDiff",timeDiff);
//    return "resume";
//    }

    public static void main(String[] args) {
        LocalDate now = LocalDate.now();
        LocalDate of = LocalDate.of(2015, 9, 10);
        Period between = Period.between(of, now);
        System.out.println(between.getYears());
        System.out.println(between.getMonths());
        System.out.println(between.getDays());
    }
}
