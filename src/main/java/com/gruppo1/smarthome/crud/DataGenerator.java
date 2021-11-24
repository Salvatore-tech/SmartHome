package com.gruppo1.smarthome.crud;

import com.gruppo1.smarthome.crud.api.Actions;
import com.gruppo1.smarthome.crud.api.CrudOperation;
import com.gruppo1.smarthome.crud.beans.CrudOperationExecutor;
import com.gruppo1.smarthome.model.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class DataGenerator {

    public static List<Profile> generateProfiles(CrudOperation addOperation, CrudOperationExecutor operationExecutor) {
        List<Profile> profiles = new ArrayList<>();
        profiles.add(new Profile("sextence0@fc2.com", "Nancy", "Jones", "pluto88"));
        profiles.add(new Profile("mariorossi@n2o.com", "Guest", "account", "tomato"));

        // save a few profiles
        profiles.forEach(profile -> operationExecutor.execute(addOperation, profile));
        return profiles;
    }

    public static List<Room> generateRooms(CrudOperation addOperation, CrudOperationExecutor operationExecutor) {
        List<Room> rooms = new ArrayList<>();
        rooms.add(new Room("Hall"));
        rooms.add(new Room("Bathroom"));
        rooms.add(new Room("Default"));
        rooms.add(new Room("Bedroom 2"));
        rooms.add(new Room("Kitchen"));
        rooms.add(new Room("Studio"));
        rooms.add(new Room("Living room"));
        rooms.add(new Room("Guest room"));

        // save a few rooms
        rooms.forEach(room -> operationExecutor.execute(addOperation, room));
        return rooms;
    }

    public static List<Device> generateDevices(CrudOperation addOperation, CrudOperationExecutor operationExecutor, List<Room> rooms) {
        Random rand = new Random();
        List<Device> devices = new ArrayList<>();
        devices.add(new AlarmClock("Spongebob block", "Alarm clock", "7:00 AM", "Workday", "Rain drops"));
        devices.add(new Conditioner("Samsung air", "Conditioner", 20, "Default"));
        devices.add(new Conditioner("Dyson", "Conditioner", 30, "Sun"));
        devices.add(new LightBulb("Desk lamp", "Lightbulb", 50, "Normal"));
        devices.add(new LightBulb("Studio lights", "Lightbulb", 75, "Cold"));
        devices.add(new LightBulb("Bedroom 1 ambient", "Lightbulb", 35, "Warm"));
        devices.add(new LightBulb("Living rgb", "Lightbulb", 65, "Variable"));
        devices.add(new Speaker("Soundbar", "Speaker", 100, "Bose", "SCS-2021"));
        devices.add(new Speaker("Woofer kit", "Speaker", 20, "Aiwa", "XR-76"));
        devices.add(new Television("Tv living", "Television", "Samsung", "TGKSLS09", 15, 0));
        devices.add(new Television("Tv kitchen", "Television", "LG", "TGKSLS09", 30, 8));

        // save a few devices
        devices.forEach(device -> {
            device.setRoom(rooms.get(rand.nextInt(rooms.size())));
            operationExecutor.execute(addOperation, device);
        });
        return devices;
    }

    public static List<Scene> generateScenes(CrudOperation addOperation, CrudOperationExecutor operationExecutor) {
        List<Scene> scenes = new ArrayList<>();
        scenes.add(new Scene("Work"));
        scenes.add(new Scene("Rest"));
        scenes.add(new Scene("Birthday reminder"));
        scenes.add(new Scene("Weekends wake up"));
        scenes.add(new Scene("General shutdown"));

        //save a few scenes
        scenes.forEach(scene ->
                operationExecutor.execute(addOperation, scene));
        return scenes;
    }

    public static List<Condition> generateConditions(CrudOperation addOperation, CrudOperationExecutor operationExecutor, List<Device> devices, List<Scene> scenes) {
        Random rand = new Random();
        List<Condition> conditions = new ArrayList<>();
        int noScenes = scenes.size();
        int noDevices = devices.size();

//        conditions.add(new Condition("One", devices.get(0), scenes.get(rand.nextInt(noScenes)), Actions.POWER_ON, 10.0));
        conditions.add(new Condition("Test", Actions.WARMER, new Date(System.currentTimeMillis()), 15.0, devices.get(1), scenes.get(rand.nextInt(noScenes)), "NONE"));
//        conditions.add(new Condition("Three", devices.get(1), scenes.get(rand.nextInt(noScenes)), Actions.WARMER, 10.0));
//        conditions.add(new Condition("Four", devices.get(4), scenes.get(rand.nextInt(noScenes)), Actions.POWER_ON, 10.0));
//        conditions.add(new Condition("Five", devices.get(rand.nextInt(noDevices)), scenes.get(rand.nextInt(noScenes)), null, 10.0));
//        conditions.add(new Condition("Six", devices.get(rand.nextInt(noDevices)), scenes.get(rand.nextInt(noScenes)), null, 10.0));
//        conditions.add(new Condition("Seven", devices.get(rand.nextInt(noDevices)), scenes.get(rand.nextInt(noScenes)), null, 10.0));
//        conditions.add(new Condition("Eight", devices.get(rand.nextInt(noDevices)), scenes.get(rand.nextInt(noScenes)), null, 10.0));
//        conditions.add(new Condition("Nine", devices.get(rand.nextInt(noDevices)), scenes.get(rand.nextInt(noScenes)), null, 10.0));
//        conditions.add(new Condition("Ten", devices.get(rand.nextInt(noDevices)), scenes.get(rand.nextInt(noScenes)), null, 10.0));

        conditions.forEach(condition -> operationExecutor.execute(addOperation, condition));
        return conditions;
    }
}
