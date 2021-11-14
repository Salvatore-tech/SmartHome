package com.gruppo1.smarthome;

import com.gruppo1.smarthome.crud.DataGenerator;
import com.gruppo1.smarthome.crud.api.CrudOperation;
import com.gruppo1.smarthome.crud.beans.CrudOperationExecutor;
import com.gruppo1.smarthome.crud.impl.AddOperationImpl;
import com.gruppo1.smarthome.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootApplication
public class SmartHomeApplication {
    private static final Logger log = LoggerFactory.getLogger(SmartHomeApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SmartHomeApplication.class, args);

    }

    @Bean
    public CommandLineRunner insertData(CrudOperationExecutor operationExecutor) {
        AtomicInteger rowNum = new AtomicInteger();
        CrudOperation addOperation = new AddOperationImpl();

        final List<Profile> profiles = DataGenerator.generateProfiles(addOperation, operationExecutor);
        final List<Room> rooms = DataGenerator.generateRooms(addOperation, operationExecutor);
        final List<Device> devices = DataGenerator.generateDevices(addOperation, operationExecutor, rooms);
        final List<Scene> scenes = DataGenerator.generateScenes(addOperation, operationExecutor);
        final List<Condition> conditions = DataGenerator.generateConditions(addOperation, operationExecutor, devices, scenes);

        return args -> {
            log.info("-------------------------------");
            log.info("Profiles inserted at start-up");
            profiles.forEach(profile -> log.info(rowNum.getAndIncrement() + ") " + profile.toString()));

            rowNum.getAndSet(0);
            log.info("-------------------------------");
            log.info("Rooms inserted at start-up");
            rooms.forEach(room -> log.info(rowNum.getAndIncrement() + ") " + room.toString()));

            rowNum.getAndSet(0);
            log.info("-------------------------------");
            log.info("Devices inserted at start-up");
            devices.forEach(device -> log.info(rowNum.getAndIncrement() + ") " + device.toString()));

            rowNum.getAndSet(0);
            log.info("-------------------------------");
            log.info("Scenes inserted at start-up");
            scenes.forEach(scene -> log.info(rowNum.getAndIncrement() + ") " + scene.toString()));

            rowNum.getAndSet(0);
            log.info("-------------------------------");
            log.info("Conditions inserted at start-up");
            conditions.forEach(condition -> log.info(rowNum.getAndIncrement() + ") " + condition.toString()));
            log.info("-------------------------------");
        };
    }
}


