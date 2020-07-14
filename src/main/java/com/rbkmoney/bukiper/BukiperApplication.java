package com.rbkmoney.bukiper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class BukiperApplication extends SpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(BukiperApplication.class, args);
    }

}
