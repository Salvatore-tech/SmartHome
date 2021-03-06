package com.gruppo1.smarthome;

import com.gruppo1.smarthome.beans.DataGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SmartHomeApplication {
    final DataGenerator dataGenerator;

    @Autowired
    public SmartHomeApplication(DataGenerator dataGenerator) {
        this.dataGenerator = dataGenerator;
    }

    public static void main(String[] args) {
        SpringApplication.run(SmartHomeApplication.class, args);
    }
}


