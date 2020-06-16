package top.i7un.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import top.i7un.springboot.service.UpDownLoadService;

/**
 * Created by Noone on 2020/6/16.
 */
@RequestMapping("/upDown")
@Controller
public class UpDownLoadController {

    @Autowired
    private UpDownLoadService upDownLoadService;

    @RequestMapping
    public String downMyResume(){

        return null;
    }
}
