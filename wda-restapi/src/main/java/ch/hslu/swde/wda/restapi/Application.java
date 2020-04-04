package ch.hslu.swde.wda.restapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"ch.hslu.swde.wda.service", "ch.hslu.swde.wda.interfaces", "ch.hslu.swde.wda.restapi"})
@EntityScan("ch.hslu.swde.wda.domain")
@EnableJpaRepositories("ch.hslu.swde.wda.repository")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
