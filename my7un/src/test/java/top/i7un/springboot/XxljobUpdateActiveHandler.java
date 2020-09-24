package top.i7un.springboot;//package com.hsong.web.server.handler.job;
//
//import com.csvreader.CsvReader;
//import com.hsong.oauth.service.IHsWxActiveUserService;
//import com.xxl.job.core.biz.model.ReturnT;
//import com.xxl.job.core.handler.annotation.XxlJob;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.io.*;
//import java.nio.charset.Charset;
//
///**
// * @author guozhenbin
// * @date 2020/9/18
// */
//@Component
//@Slf4j
//public class XxljobUpdateActiveHandler {
//
//    @Autowired
//    private IHsWxActiveUserService hsWxActiveUserService;
//    @XxlJob(value = "XxljobUpdateActiveHandler")
//    public ReturnT<String> updateStartTime(String param) {
//        log.info("xxlJobUpdateRoomCourseStartTime start... param:{}", param);
//        CsvReader reader = null;
//
//        try {
//            char separator = ',';
//            reader = new CsvReader("/Users/zhaojun/Desktop/1.csv", separator, Charset.forName("UTF-8"));
//            reader.readHeaders();
//            while (reader.readRecord()) {
//                // 读一整行
////                System.out.println(reader.getRawRecord());
////                // 读这行的第一列
////                System.out.println(reader.get("学号"));
////                // 读这行的第二列
////                System.out.println(reader.get(1));
//
//                hsWxActiveUserService.saveBatch()
//                System.out.println("INSERT INTO `hs_wx_active_user`(`appid`, `openid`, `unionid`, `userid`, `active_time`, `active_method`, `active_source`, `active_activity`, `auth_status`, `auth_method`, `auth_source`, `auth_activity`, `create_time`, `phone`) VALUES " +
//                        "('wxa7740225caabc3ea', '"+reader.get(4)+"', '', '"+reader.get(2)+"', '"+reader.get(3)+"', 'wxOther', '', '', 1, '', '', '',  '"+reader.get(3)+"',  '');");
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }finally {
//            if (null != reader) {
//                reader.close();
//            }
//        }
//
//        log.info("xxlJobUpdateRoomCourseStartTime end");
//        return ReturnT.SUCCESS;
//    }
//
//    private  int splitFile(String fileName, String pathName) {
//        int rownum = 1;
//
//        int fileNo = 1;
//        try {
//            FileReader read = new FileReader(fileName);
//            BufferedReader br = new BufferedReader(read);
//            String row;
//
//            FileWriter fw = new FileWriter(pathName + fileNo + ".csv");
//            while ((row = br.readLine()) != null) {
//                rownum++;
//                fw.append(row + "\r\n");
//
//                if ((rownum / 5000) > (fileNo - 1)) {
//                    fw.close();
//                    fileNo++;
//                    fw = new FileWriter(pathName + fileNo + ".csv");
//                }
//            }
//            fw.close();
//            log.info("切割大csv成功 rownum=" + rownum + ";fileNo=" + fileNo);
//        } catch (FileNotFoundException e) {
//            log.info("切割csv文件filenotFound",e);
//        } catch (IOException e) {
//            log.info("切割csv文件异常",e);
//        }
//        return fileNo;
//    }
//
//}
