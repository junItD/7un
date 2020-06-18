package top.i7un.springboot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
        DataMap data = resumeService.findAllwork();
        return JsonResult.build(data).toJSON();
    }

    @RequestMapping(value = "/getTimeDiff", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getTimeDiff(){
        Period period = Period.between(LocalDate.of(2015, 9, 10), LocalDate.now());
        String timeDiff = period.getYears() + "年" + period.getMonths() + "个月零" + period.getDays() + "天";
        DataMap<Object> objectDataMap = DataMap.success().setData(timeDiff);
        return JsonResult.build(objectDataMap).toJSON();
    }

    @RequestMapping(value = "/getWorkRecord" ,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getWorkRecord(@RequestParam("workId")String  workId){
        DataMap data = resumeService.getWorkRecordByWorkId(workId);
        return JsonResult.build(data).toJSON();
    }
}
