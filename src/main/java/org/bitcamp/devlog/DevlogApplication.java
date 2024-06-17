package org.bitcamp.devlog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication()
@ComponentScan({"org.bitcamp.devlog.*"})
public class DevlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(DevlogApplication.class, args);
    }
}
