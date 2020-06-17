package top.i7un.springboot.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.CodingUtils;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import top.i7un.springboot.constant.OSSClientConstants;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author:  Noone
 * @Date: 2018/7/21 11:29
 * Describe: 文件工具
 */
public class FileUtil {

    /**
     * 上传文件到阿里云OSS
     * @param file 文件流
     * @return 返回文件URL
     */
    public String uploadFile(File file, String subCatalog){

        //初始化OSSClient
        OSSClient ossClient = AliYunOSSClientUtil.getOSSClient();

        String md5Key = AliYunOSSClientUtil.uploadObject2OSS(ossClient, file, OSSClientConstants.BACKET_NAME,
                OSSClientConstants.FOLDER + subCatalog + "/");
        String url = AliYunOSSClientUtil.getUrl(ossClient, md5Key);
        String picUrl = "https://" + OSSClientConstants.BACKET_NAME + "." + OSSClientConstants.ENDPOINT +
                "/" + OSSClientConstants.FOLDER + subCatalog + "/" + file.getName();

        //删除临时生成的文件
        File deleteFile = new File(file.toURI());
        deleteFile.delete();

        return picUrl;

    }

    /**
     * base64字符转换成file
     *  @param destPath 保存的文件路径
     * @param base64 图片字符串
     * @param fileName 保存的文件名
     * @return file
     */
    public File base64ToFile(String destPath,String base64, String fileName) {
        File file = null;
        //创建文件目录
        String filePath=destPath;
        File  dir=new File(filePath);
        if (!dir.exists() && !dir.isDirectory()) {
            dir.mkdirs();
        }
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        try {
            byte[] bytes = Base64.getDecoder().decode(base64);
            file=new File(filePath+"/"+fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return file;
    }

    /**
     * 将file转换成base64字符串
     * @param path
     * @return
     */
    public String fileToBase64(String path) {
        String base64 = null;
        InputStream in = null;
        try {
            File file = new File(path);
            in = new FileInputStream(file);
            byte[] bytes=new byte[(int)file.length()];
            in.read(bytes);
            base64 = Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return base64;
    }

    /**
     * MultipartFile类型文件转File
     * @return File类型文件
     */
    public File multipartFileToFile(MultipartFile multipartFile, String filePath, String fileName){
        File f = null;
        File dir = new File(filePath);
        if (!dir.exists() && !dir.isDirectory()) {
            dir.mkdirs();
        }
        if(StringUtil.BLANK.equals(multipartFile) || multipartFile.getSize() <= 0){
            multipartFile = null;
        } else {
            try {
                InputStream ins = multipartFile.getInputStream();
                f = new File(filePath + fileName);
                OutputStream os = new FileOutputStream(f);
                int bytesRead = 0;
                byte[] buffer = new byte[8192];
                while ((bytesRead = ins.read(buffer, 0, 8192)) != -1){
                    os.write(buffer, 0, bytesRead);
                }
                os.close();
                ins.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return f;
    }

    public static void downLoadAndSout() throws IOException {
        String bucketName = OSSClientConstants.BACKET_NAME;
        String objectName = "resume.doc";
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


    public static void downLoadFile(String file){
        OSSClient ossClient = AliYunOSSClientUtil.getOSSClient();
        // 下载OSS文件到本地文件。如果指定的本地文件存在会覆盖，不存在则新建。
        ossClient.getObject(new GetObjectRequest(OSSClientConstants.BACKET_NAME, file), new File(file));
        // 关闭OSSClient。
        ossClient.shutdown();
    }


}
