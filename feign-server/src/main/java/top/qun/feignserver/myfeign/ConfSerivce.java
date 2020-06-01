package top.qun.feignserver.myfeign;

import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
public class ConfSerivce implements ApplicationListener<WebServerInitializedEvent> {
    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        try {
            int port = event.getWebServer().getPort();
            System.out.println("*******************************************"+port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}