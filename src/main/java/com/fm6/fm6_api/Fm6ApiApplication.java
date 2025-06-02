package com.fm6.fm6_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication(scanBasePackages = { "com.fm6.fm6_api", "com.fm6.repository" })
@EnableJpaRepositories(basePackages    = { "com.fm6.repository" })
public class Fm6ApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(Fm6ApiApplication.class, args);
    }
}
