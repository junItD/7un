package top.i7un.springboot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.i7un.springboot.constant.CodeType;
import top.i7un.springboot.service.ResumeService;
import top.i7un.springboot.utils.DataMap;
import top.i7un.springboot.utils.JsonResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Noone on 2020/6/4.
 */
@RestController
@Slf4j
public class ResumeController {

    @Autowired
    private ResumeService resumeService;

    @RequestMapping(value = "/getWork" , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getWork(){
        try {
            DataMap data = resumeService.findAllwork();
//            System.out.println(JsonResult.build(data).toJSON());
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            log.error("工作信息获取失败", e);
        }
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();

    }

    @RequestMapping(value = "/getTimeDiff", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getTimeDiff(){
        Period period = Period.between(LocalDate.of(2015, 9, 10), LocalDate.now());
        String timeDiff = period.getYears() + "年" + period.getMonths() + "个月零" + period.getDays() + "天";
//        model.addAttribute("timeDiff",timeDiff);
        DataMap<Object> objectDataMap = DataMap.success().setData(timeDiff);
        return JsonResult.build(objectDataMap).toJSON();
    }

}
