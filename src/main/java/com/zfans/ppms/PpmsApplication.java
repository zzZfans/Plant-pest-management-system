package com.zfans.ppms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
@MapperScan("com.zfans.ppms.mapper")
public class PpmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(PpmsApplication.class, args);
    }

}
