package top.i7un.springboot.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName: ZipUtil
 * @Description: 压缩解压文件
 * @author gaoyuxin
 * @date 2018年4月26日 下午4:57:17
 */

public class ZipUtil {  
  
	private static Logger logger = LoggerFactory.getLogger(ZipUtil.class);
	
    private static int k = 1; // 定义递归次数变量  
    
    /**
     * 
     * @Description (压缩)
     * @throws Exception
     */
    private static void compression(String inputFilePath, String outPutZip) { 
        try {
            String zipFileName = outPutZip;
            File inputFile = new File(inputFilePath);
            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName));  
            BufferedOutputStream bo = new BufferedOutputStream(out);  
            zip(out, inputFile, inputFile.getName(), bo);
            bo.close();  
            out.close(); // 输出流关闭  
        } catch (Exception e) {
        	logger.warn("ZipUtil compression exception,",e);
        }  
    }  
  
    private static void zip(ZipOutputStream out, File inputFile, String base, BufferedOutputStream bo) throws Exception { // 方法重载  
        if (inputFile.isDirectory()) {  
            File[] fl = inputFile.listFiles();  
            if (fl.length == 0) {  
                out.putNextEntry(new ZipEntry(base + "/")); // 创建zip压缩进入点base  
            }  
            for (int i = 0; i < fl.length; i++) {  
                zip(out, fl[i], base + "/" + fl[i].getName(), bo); // 递归遍历子文件夹  
            }  
            k++;  
        } else {  
            out.putNextEntry(new ZipEntry(base)); // 创建zip压缩进入点base  
            FileInputStream in = new FileInputStream(inputFile);  
            BufferedInputStream bi = new BufferedInputStream(in);  
            int b;  
            while ((b = bi.read()) != -1) {  
                bo.write(b); // 将字节流写入当前zip目录  
            }  
            bi.close();  
            in.close(); // 输入流关闭  
        }  
    }  
    
    
   /**
    * 
    * @Description (解压)
    * @param zipPath zip路径
    * @param charset 编码
    * @param outPutPath 输出路径
    */
    private static void decompression(String zipPath, String charset, String outPutPath) {
        try {  
            ZipInputStream Zin=new ZipInputStream(new FileInputStream(zipPath), Charset.forName(charset));//输入源zip路径  
            BufferedInputStream Bin=new BufferedInputStream(Zin);  
            String Parent = outPutPath; //输出路径（文件夹目录）  
            File Fout=null;  
            ZipEntry entry;  
            try {  
                while((entry = Zin.getNextEntry())!=null && !entry.isDirectory()){  
                    Fout=new File(Parent,entry.getName());  
                    if(!Fout.exists()){  
                        (new File(Fout.getParent())).mkdirs();  
                    }  
                    FileOutputStream out=new FileOutputStream(Fout);  
                    BufferedOutputStream Bout=new BufferedOutputStream(out);  
                    int b;  
                    while((b=Bin.read())!=-1){  
                        Bout.write(b);  
                    }  
                    Bout.close();  
                    out.close();  
                }  
                Bin.close();  
                Zin.close();  
            } catch (IOException e) {  
            	logger.warn("ZipUtil decompression exception,",e);
            }  
        } catch (FileNotFoundException e) {  
        	logger.warn("ZipUtil decompression exception,",e);
        }  
    }  
    
    public static void main(String[] args) { 
       compression("D:\\鞋子.xlsx", "D:\\aa.zip");
       decompression("D:\\aaaa\\鞋子.zip", "GBK", "D:\\aaaa");
    }
  
}
