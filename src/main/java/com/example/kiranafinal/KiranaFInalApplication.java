package com.example.kiranafinal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableCaching
@EnableScheduling
public class KiranaFInalApplication {

    public static void main(String[] args) {
        System.out.println("Started KiranaFInalApplication");
        SpringApplication.run(KiranaFInalApplication.class, args);
        System.out.println("Finished KiranaFInalApplication");
    }

}
