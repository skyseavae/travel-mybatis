package com.skysean.mybatis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MybatisApplication {

    public static void main(String[] args) {
        new SpringApplication(new Object[]{
                MybatisApplication.class
        }).run(args);
    }
}
