package net.devgrr.springbootinit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SpringBootInitApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootInitApplication.class, args);
    }

}
