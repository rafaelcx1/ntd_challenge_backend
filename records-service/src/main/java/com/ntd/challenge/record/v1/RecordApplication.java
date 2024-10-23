package com.ntd.challenge.record.v1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class RecordApplication {

    public static void main(String[] args) {
        SpringApplication.run(RecordApplication.class, args);
    }
}
