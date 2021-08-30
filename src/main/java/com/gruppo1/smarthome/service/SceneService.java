package com.gruppo1.smarthome.service;

import com.gruppo1.smarthome.model.Conditions;
import com.gruppo1.smarthome.model.Device;
import com.gruppo1.smarthome.model.Scene;
import com.gruppo1.smarthome.repository.DeviceRepo;
import com.gruppo1.smarthome.repository.SceneRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SceneService {
    @Autowired
    private final SceneRepo sceneRepo;
    @Autowired
    private final DeviceRepo deviceRepo;

    public SceneService(SceneRepo sceneRepo, DeviceRepo deviceRepo) {
        this.sceneRepo = sceneRepo;
        this.deviceRepo = deviceRepo;
    }


    public Scene addScene(Scene scene) {
        return sceneRepo.save(scene);
    }

    public Iterable<Scene> findAllScene() {
        return sceneRepo.findAll();
    }

    public Scene updateScene(Scene scene) {
        if (sceneRepo.findById(scene.getId()).isPresent()) {
            sceneRepo.save(scene);
            return scene;
        }
        return null;
    }

    public Optional<Scene> findSceneByID(String id) {
        return sceneRepo.findById(id);
    }

    public Boolean deleteScene(String id) {
        Optional<Scene> scene = sceneRepo.findById(id);
        if (scene.isPresent()) {
            sceneRepo.deleteSceneById(id);
            return true;
        }
        return false;
    }

    public Optional<Device> addDevice(String sceneName, String deviceName) {
        Optional<Scene> scene = sceneRepo.findByName(sceneName);
        Optional<Device> device = deviceRepo.findByName(deviceName);
        //TODO: check if already present (duplicate foreign key)
        if (scene.isPresent() && device.isPresent()) {
            scene.get().addCondition(new Conditions(device.get(), scene.get()));
        }
        //TODO: change return value
        return device;
    }

    //Thread starvation/clock leap?
    public Optional<Device> removeDevice(String sceneName, String deviceName) {
        Optional<Scene> scene = sceneRepo.findByName(sceneName);
        Optional<Device> device = deviceRepo.findByName(deviceName);
        if (scene.isPresent() && device.isPresent()) {
            scene.get().removeCondition(new Conditions(device.get(), scene.get()));
        }
        return device;
    }

    public List<Device> findDevices(String sceneName) {
        Optional<Scene> scene = sceneRepo.findByName(sceneName);
        List<Device> devices = sceneRepo.findAllDevices(scene);
        return devices;
    }


}
