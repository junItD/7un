import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Created by Noone on 2020/5/25.
 */
@SpringBootApplication
@EnableEurekaServer
public class My7unEurekaApplication {
    public static void main(String[] args) {
        SpringApplication.run(My7unEurekaApplication.class, args);
    }
}
