package top.i7un.springboot.utils;

import java.io.*;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;


/**
 * @ClassName: FtpUtils
 * @Description: ft工具类
 * @author gaoyuxin
 * @date 2018年4月26日 下午4:50:10
 */
public class FtpUtils {

    public static String downLoad(String host,String username,String password,String serverDir,String fileName,String localPath){
        FTPClient client = new FTPClient();
        try {
            client.connect(host);
            client.login(username, password);
            int reply = client.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                client.disconnect();
            }
            //转移到FTP服务器目录
            client.changeWorkingDirectory(serverDir);
            client.enterLocalPassiveMode();
            client.setControlKeepAliveTimeout(15000);
            client.setFileType(FTPClient.BINARY_FILE_TYPE);
            FTPFile[] fs = client.listFiles(fileName);
            for(FTPFile ff:fs){
                if(ff.getSize()>0){
                    FileUtils.checkDir(localPath);
                    //下载到本地路径
                    String localFile = localPath+ File.separatorChar+ ff.getName();
                    OutputStream is = new FileOutputStream(localFile);
                    client.retrieveFile(ff.getName(), is);
                    is.flush();
                    is.close();
                    return localFile;
                }
            }
            throw new RuntimeException("服务器没有文件"+fileName+"下载失败");
        }catch (Exception e){
            throw new RuntimeException("下载文件失败"+fileName,e);
        }finally {
            try {
                client.logout();
            } catch (IOException e) {

            }
            try {
                client.disconnect();
            } catch (IOException e) {

            }
        }
    }

    public static String upLoad(String host,String username,String password,String serverDir,String fileName,String localPath){
        FTPClient client = new FTPClient();
        try {
            client.connect(host);
            client.login(username, password);
            int reply = client.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                client.disconnect();
            }
            //上传到FTP服务器哪个目录
            client.changeWorkingDirectory(serverDir);
            client.enterLocalPassiveMode();
            client.setControlKeepAliveTimeout(6000);
            File file=new File(localPath);
            if (file.exists()) {
                File[] files = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    if (files[i].isFile()) {
                        if (files[i].getName().equals(fileName)) {
                            FileInputStream inputStream=new FileInputStream(files[i]);
                            client.storeFile(fileName,inputStream);
                            inputStream.close();
                        }
                    }
                }
                return fileName+"上传成功！";
            }
            throw new RuntimeException("没有文件"+fileName+"上传失败");
        }catch (Exception e){
            throw new RuntimeException("上传文件失败"+fileName,e);
        }finally {
            try {
                client.logout();
            } catch (IOException e) {

            }
            try {
                client.disconnect();
            } catch (IOException e) {

            }
        }
    }
}
