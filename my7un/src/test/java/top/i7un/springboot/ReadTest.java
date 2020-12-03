package top.i7un.springboot;

import com.csvreader.CsvReader;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Noone on 2020-09-21.
 */
public class ReadTest {
    public static char separator = ',';

    public static void main(String[] args) {
        CsvReader reader = null;
        List<String[]> dataList = new ArrayList<String[]>();
        try {
            //如果生产文件乱码，windows下用gbk，linux用UTF-8
            reader = new CsvReader("/Users/zhaojun/Desktop/11.csv", separator, Charset.forName("UTF-8"));

            // 读取表头
            reader.readHeaders();
//            String[] headArray = reader.getHeaders();//获取标题
//            System.out.println(headArray[0] + headArray[1] + headArray[2]);

            // 逐条读取记录，直至读完
            StringBuilder stringBuilder = new StringBuilder();
            while (reader.readRecord()) {
                // 读一整行
//                System.out.println(reader.getRawRecord());
//                // 读这行的第一列
//                System.out.println(reader.get("学号"));
//                // 读这行的第二列
//                System.out.println(reader.get(1));
//2018586

//                System.out.println("INSERT INTO `hs_wx_active_user`(`appid`, `openid`,  `userid`, `active_time`,    `create_time`, `channel`) VALUES " +
//                        "('wxa7740225caabc3ea', '"+reader.get(0)+"',  '"+reader.get(1)+"', '"+reader.get(3)+"',   '"+reader.get(3)+"',  '"+reader.get(2)+"');\n");
//                stringBuilder.append("INSERT INTO `hs_wx_active_user`(`appid`, `openid`,  `userid`, `active_time`,    `create_time`, `channel`) VALUES " +
//                        "('wxa7740225caabc3ea', '"+reader.get(0)+"',  '"+reader.get(1)+"', '"+reader.get(3)+"',   '"+reader.get(3)+"',  '"+reader.get(2)+"');\n");



//                System.out.println("INSERT INTO `hs_channel_options_new` ( `code`,`name`, `parent_code`, `type`,`value` ) " +
//                        "VALUES " +
//                        "\t( '"+reader.get(0)+"','"+reader.get(1)+"', 'zh', 0,'"+reader.get(2)+"');");


                System.out.println("INSERT INTO `hs_channel_options_new` ( `code`,`name`, `parent_code`, `type` ) " +
                        "VALUES " +
                        "\t( '"+reader.get(0)+"','"+reader.get(1)+"', 'gzhwzdl', 0 );");

            }
//            File file = new File("/Users/zhaojun/Desktop/sql.txt");//文件路径（路径+文件名）
//            if (!file.exists()) {   //文件不存在则创建文件，先创建目录
//                File dir = new File(file.getParent());
//                dir.mkdirs();
//                file.createNewFile();
//            }
//            FileOutputStream outStream = new FileOutputStream(file); //文件输出流将数据写入文件
//            outStream.write(stringBuilder.toString().getBytes());
//            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != reader) {
                reader.close();
            }
        }
    }

    public static String ToFirstChar(String chinese){
        String pinyinStr = "";
        char[] newChar = chinese.toCharArray();  //转为单个字符
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < newChar.length; i++) {
            if (newChar[i] > 128) {
                try {
                    pinyinStr += PinyinHelper.toHanyuPinyinStringArray(newChar[i], defaultFormat)[0].charAt(0);
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            }else{
                pinyinStr += newChar[i];
            }
        }
        return pinyinStr;
    }
}
