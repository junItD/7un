package top.i7un.springboot;

import freemarker.template.TemplateException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.i7un.springboot.mapper.VisitorMapper;
import top.i7un.springboot.mapper.WorkMapper;
import top.i7un.springboot.model.Work;
import top.i7un.springboot.mytest.FreeMarkTemplateService;

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
}
