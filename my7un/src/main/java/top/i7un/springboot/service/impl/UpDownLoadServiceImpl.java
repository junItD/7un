package top.i7un.springboot.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.OSSObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import top.i7un.springboot.constant.OSSClientConstants;
import top.i7un.springboot.service.UpDownLoadService;
import top.i7un.springboot.utils.AliYunOSSClientUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Noone on 2020/6/16.
 */
@Service
public class UpDownLoadServiceImpl implements UpDownLoadService {


    @Override
    public void downLooad() throws IOException {
        downLoadFile();

    }

    public void downLoadFile() throws IOException {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
// 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = OSSClientConstants.ACCESS_KEY_ID;
        String accessKeySecret = OSSClientConstants.ACCESS_KEY_SECRET;
        String bucketName = OSSClientConstants.BACKET_NAME;
//<yourObjectName>表示从OSS下载文件时需要指定包含文件后缀在内的完整路径，例如abc/efg/123.jpg。
        String objectName = "/oss.txt";

// 创建OSSClient实例。
        OSS ossClient = AliYunOSSClientUtil.getOSSClient();

// ossObject包含文件所在的存储空间名称、文件名称、文件元信息以及一个输入流。
        OSSObject ossObject = ossClient.getObject(bucketName, objectName);

// 读取文件内容。
        System.out.println("Object content:");
        BufferedReader reader = new BufferedReader(new InputStreamReader(ossObject.getObjectContent()));
        while (true) {
            String line = reader.readLine();
            if (line == null) break;

            System.out.println("\n" + line);
        }
// 数据读取完成后，获取的流必须关闭，否则会造成连接泄漏，导致请求无连接可用，程序无法正常工作。
        reader.close();

// 关闭OSSClient。
        ossClient.shutdown();
    }
}
