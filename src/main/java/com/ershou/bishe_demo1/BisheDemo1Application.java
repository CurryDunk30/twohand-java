package com.ershou.bishe_demo1;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.ershou.bishe_demo1.Mapper")
public class BisheDemo1Application {

    public static void main(String[] args) {
        SpringApplication.run(BisheDemo1Application.class, args);
    }

}
