package top.i7un.springboot.controller;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.OSSObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import top.i7un.springboot.constant.OSSClientConstants;
import top.i7un.springboot.service.UpDownLoadService;
import top.i7un.springboot.utils.AliYunOSSClientUtil;
import top.i7un.springboot.utils.FileUtil;
import top.i7un.springboot.utils.FileUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * Created by Noone on 2020/6/16.
 */
@RequestMapping("/upDown")
@Controller
public class UpDownLoadController {

    @Autowired
    private UpDownLoadService upDownLoadService;

    /**
     * 这块实现的有点差  思路是 下载到本地 然后从本地读流放到response中返回给前端  有待改进
     * @param response
     * @throws IOException
     */
    @RequestMapping
    public void downMyResume(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String filePath = request.getParameter("filePath");
        FileUtil.downLoadFile(filePath);
        FileInputStream ips = new FileInputStream(filePath);
        ServletOutputStream out = response.getOutputStream();
        int len = 0;
        byte[] arr = new byte[1024*10];
        while((len = ips.read(arr)) != -1){
            out.write(arr,0,len);
        }
        out.flush();
        response.setContentType("application/multipart/form-data");
        response.setContentType("charaset=utf-8");
        /* 设置文件头：最后一个参数是设置下载文件名   */
        response.setHeader("Content-Disposition", "attachment");
        FileUtils.removeFile(filePath);

    }
}
