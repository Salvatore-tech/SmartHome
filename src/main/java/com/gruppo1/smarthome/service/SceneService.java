package com.gruppo1.smarthome.service;

import com.gruppo1.smarthome.crud.api.CrudOperation;
import com.gruppo1.smarthome.crud.api.SmartHomeItemLight;
import com.gruppo1.smarthome.crud.beans.CrudOperationExecutor;
import com.gruppo1.smarthome.crud.impl.*;
import com.gruppo1.smarthome.crud.memento.MementoCareTaker;
import com.gruppo1.smarthome.model.Conditions;
import com.gruppo1.smarthome.model.Device;
import com.gruppo1.smarthome.model.Scene;
import com.gruppo1.smarthome.model.SmartHomeItem;
import com.gruppo1.smarthome.repository.DeviceRepo;
import com.gruppo1.smarthome.repository.SceneRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class SceneService {
    private final SceneRepo sceneRepo;
    private final DeviceRepo deviceRepo;
    private CrudOperationExecutor operationExecutor;
    private MementoCareTaker mementoCareTaker;


    @Autowired
    public SceneService(SceneRepo sceneRepo, DeviceRepo deviceRepo, CrudOperationExecutor operationExecutor, MementoCareTaker mementoCareTaker) {
        this.sceneRepo = sceneRepo;
        this.deviceRepo = deviceRepo;
        this.operationExecutor = operationExecutor;
        this.mementoCareTaker = mementoCareTaker;
    }


    public Scene addScene(Scene scene) {
        if(Objects.nonNull(this.findSceneByName(scene.getName())))
            return null;
        CrudOperation operationToPerform = new AddOperationImpl();
        mementoCareTaker.add(operationToPerform.generateMemento(), new SmartHomeItemLight(scene.getName(), "Scene"));
        return (Scene) operationExecutor.execute(operationToPerform, scene);
    }

    public List<Scene> findAllScene() {
        CrudOperation operationToPerform = new GetOperationImpl();
        mementoCareTaker.add(operationToPerform.generateMemento(), new SmartHomeItemLight(null, "Sceme"));
        return (List<Scene>) operationExecutor.execute(operationToPerform, this);
    }

    public Scene findSceneByName(String name) {
        CrudOperation operationToPerform = new GetByNameOperationImpl();
        mementoCareTaker.add(operationToPerform.generateMemento(), new SmartHomeItemLight(null, "Scene"));
        return (Scene) operationExecutor.execute(operationToPerform, name, this);
    }


    public Scene updateScene(String sceneNameToUpdate, Scene updatedScene) {
//        if (Objects.nonNull(sceneRepo.findById(scene.getId()))) {
//            sceneRepo.save(scene);
//            return scene;
//        }
        //TODO SS: hide more the id handling

        Scene oldScene = (Scene) operationExecutor.execute(new GetByNameOperationImpl(), sceneNameToUpdate, this);
        if (Objects.nonNull(oldScene)) {
            updatedScene.setId(oldScene.getId());
            UpdateOperationImpl operationToPerform = new UpdateOperationImpl();
            mementoCareTaker.add(operationToPerform.generateMemento(), new SmartHomeItemLight(oldScene.getName(), "Scene"));
            return (Scene) operationExecutor.execute(operationToPerform, updatedScene);
        }
        return null;
    }


    public Integer deleteScene(String name) {
//        Optional<Scene> scene = sceneRepo.findById(id);
//        if (scene.isPresent()) {
//            sceneRepo.deleteSceneById(id);
//            return true;
//        }
        //TODO check if already exists
        SmartHomeItem sceneToDelete = (SmartHomeItem) operationExecutor.execute(new GetByNameOperationImpl(), name, this);
        DeleteOperationImpl operationToPerform = new DeleteOperationImpl();
        mementoCareTaker.add(operationToPerform.generateMemento(), new SmartHomeItemLight(sceneToDelete.getName(), "Scene")); //TODO
        return (Integer) operationExecutor.execute(operationToPerform, name, this);
    }

    // TODO SS: not yet implemented

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
//        List<Device> devices = sceneRepo.findAllDevices(scene);
        return null;
    }


}
