package com.my.user.provider2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class UserProvider2Application {

    public static void main(String[] args) {
        SpringApplication.run(UserProvider2Application.class, args);
    }

}
