package com.netcracker.hritsay.news;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.netcracker.hritsay.news.controllers", "com.netcracker.hritsay.news.services", "com.netcracker.hritsay.news.models"})
public class NewsApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(NewsApplication.class, args);
    }

}
