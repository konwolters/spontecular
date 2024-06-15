package com.example.spontecular;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example.spontecular")
public class SpontecularApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpontecularApplication.class, args);
    }
}
