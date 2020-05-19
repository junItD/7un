package top.i7un.springboot.mytest;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;

@Service
@Slf4j
public class FreeMarkTemplateService {


    @Autowired
    private Configuration freeMarkerConfiguration;

    /**
     * 根据入参解析模板
     *
     * @param templateName    模板名称
     * @param templateContext 模板
     * @param model           入参model类
     * @return afterTemplateContext    解析后的模板
     */
    public String parsingTemplate(String templateName, String templateContext, Object model) throws IOException, TemplateException {

        StringTemplateLoader stringLoader = new StringTemplateLoader();
        stringLoader.putTemplate(templateName, templateContext);
        freeMarkerConfiguration.setTemplateLoader(stringLoader);
        Template template = freeMarkerConfiguration.getTemplate(templateName);
        String s = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        System.out.println(s);
        return FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
    }

}