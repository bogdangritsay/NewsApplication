package com.netcracker.hritsay.news;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class NewsApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(NewsApplication.class, args);
    }

}
