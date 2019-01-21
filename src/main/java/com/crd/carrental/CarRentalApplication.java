package com.crd.carrental;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CarRentalApplication {
    public final static String API_VERSION_1 = "v1";

    public static void main(String[] args) {
        SpringApplication.run(CarRentalApplication.class, args);
    }

}

