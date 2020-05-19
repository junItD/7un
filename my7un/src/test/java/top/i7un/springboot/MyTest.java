package top.i7un.springboot;

import freemarker.template.TemplateException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.i7un.springboot.mytest.FreeMarkTemplateService;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Noone on 2020-05-19.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class MyTest {

    @Autowired
    private FreeMarkTemplateService freeMarkTemplateService;

    @Test
    public void test1() throws IOException, TemplateException {
        HashMap<Object, Object> map = new HashMap<>();
        map.put("username", "111");
        String s = freeMarkTemplateService.parsingTemplate("11", "select ${username}", map);
        System.out.println(s);
    }
}
