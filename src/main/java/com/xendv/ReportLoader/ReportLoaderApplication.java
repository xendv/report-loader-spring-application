package com.xendv.ReportLoader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static com.xendv.ReportLoader.conf.SecurityConfig.passwordEncoder;

@SpringBootApplication
public class ReportLoaderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReportLoaderApplication.class, args);
        System.out.println(passwordEncoder().encode("user"));
    }
}
