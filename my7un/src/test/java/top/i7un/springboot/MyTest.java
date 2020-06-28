package top.i7un.springboot;

import freemarker.template.TemplateException;
import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;
import top.i7un.springboot.mapper.VisitorMapper;
import top.i7un.springboot.mapper.WorkMapper;
import top.i7un.springboot.model.Work;
import top.i7un.springboot.mytest.FreeMarkTemplateService;
import top.i7un.springboot.utils.FileUtil;
import top.i7un.springboot.utils.FileUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Noone on 2020-05-19.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class MyTest {

    @Autowired
    private FreeMarkTemplateService freeMarkTemplateService;

    @Autowired
    private StringEncryptor stringEncryptor;

    @Test
    public void test1() throws IOException, TemplateException {
        HashMap<Object, Object> map = new HashMap<>();
        map.put("username", "111");
        String s = freeMarkTemplateService.parsingTemplate("11", "select ${username}", map);
        System.out.println(s);
    }


    @Autowired
    private WorkMapper workMapper;

    @Autowired
    private VisitorMapper visitorMapper;

    @Test
    public void test23(){
        long totalVisitor = visitorMapper.getTotalVisitor();
        System.out.println(totalVisitor);
    }
    @Test
    public void testMapper(){
        List<Work> works = workMapper.selectAllWork();
        System.out.println(works);
    }

    @Test
    public void testDel(){
        FileUtils.removeFile("resume.doc");
    }

    @Test
    public void testDown() throws IOException {
        FileUtil.downLoadFile("resume.doc");
    }

    @Test
    public void test() throws IOException {
        FileUtil.downLoadAndSout();
    }

    @Test
    public void test11(){
        System.out.println(stringEncryptor.encrypt("username"));
        System.out.println(stringEncryptor.encrypt("password"));
    }

    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String from;

    @Test
    public void testSend(){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from); // 邮件发送者
        message.setTo("347436604@qq.com"); // 邮件接受者
        message.setSubject("有人留言"); // 主题
        message.setText("好像又人留言"); // 内容
        javaMailSender.send(message);
    }
}
