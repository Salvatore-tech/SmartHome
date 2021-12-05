package com.gruppo1.smarthome;

import com.gruppo1.smarthome.model.DataGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@EnableScheduling
@SpringBootApplication
public class SmartHomeApplication {

    @Autowired
    DataGenerator dataGenerator;

    public static void main(String[] args) {
        SpringApplication.run(SmartHomeApplication.class, args);
    }
}


