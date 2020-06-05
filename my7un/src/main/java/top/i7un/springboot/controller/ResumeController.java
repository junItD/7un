package top.i7un.springboot.controller;

import com.netflix.discovery.converters.Auto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.i7un.springboot.constant.CodeType;
import top.i7un.springboot.service.ResumeService;
import top.i7un.springboot.utils.DataMap;
import top.i7un.springboot.utils.JsonResult;

/**
 * Created by Noone on 2020/6/4.
 */
@RestController
@Slf4j
public class ResumeController {

    @Autowired
    private ResumeService resumeService;

    @RequestMapping("/getWork")
    public String getWork(){
        try {
            DataMap data = resumeService.findAllwork();
            System.out.println(JsonResult.build(data).toJSON());
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            log.error("工作信息获取失败", e);
        }
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();

    }
}
