package com.oauth2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {
        "com.oauth2*"
})
@SpringBootApplication
public class ApplicationStartUp {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationStartUp.class, args);
    }
}
