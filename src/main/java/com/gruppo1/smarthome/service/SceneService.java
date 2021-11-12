package com.gruppo1.smarthome.service;

import com.gruppo1.smarthome.crud.api.CrudOperation;
import com.gruppo1.smarthome.crud.beans.CrudOperationExecutor;
import com.gruppo1.smarthome.crud.impl.*;
import com.gruppo1.smarthome.crud.memento.Memento;
import com.gruppo1.smarthome.crud.memento.MementoCareTaker;
import com.gruppo1.smarthome.model.Conditions;
import com.gruppo1.smarthome.model.Device;
import com.gruppo1.smarthome.model.Scene;
import com.gruppo1.smarthome.model.SmartHomeItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class SceneService {
    private final ConditionsService conditionsService;
    private final CrudOperationExecutor operationExecutor;
    private final MementoCareTaker mementoCareTaker;

    @Autowired
    public SceneService(ConditionsService conditionsService, CrudOperationExecutor operationExecutor, MementoCareTaker mementoCareTaker) {
        this.conditionsService = conditionsService;
        this.operationExecutor = operationExecutor;
        this.mementoCareTaker = mementoCareTaker;
    }

    public Scene addScene(Scene scene) {
        if(Objects.nonNull(this.findSceneByName(scene.getName())))
            return null;
        CrudOperation operationToPerform = new AddOperationImpl();
        mementoCareTaker.add(new Memento(operationToPerform, scene, "Add a scene"));
        return (Scene) operationExecutor.execute(operationToPerform, scene);
    }

    public List<Scene> findAllScene() {
        CrudOperation operationToPerform = new GetOperationImpl();
        mementoCareTaker.add(new Memento(operationToPerform, null, "Get all scenes"));
        return (List<Scene>) operationExecutor.execute(operationToPerform, this);
    }

    public Scene findSceneByName(String name) {
        CrudOperation operationToPerform = new GetByNameOperationImpl();
        Scene result = (Scene) operationExecutor.execute(operationToPerform, name, this);
        mementoCareTaker.add(new Memento(operationToPerform, result, "Find a scene by name"));
        return result;
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
            mementoCareTaker.add(new Memento(operationToPerform, oldScene, "Update a scene"));
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
        mementoCareTaker.add(new Memento(operationToPerform, sceneToDelete, "Delete a scene")); //TODO
        return (Integer) operationExecutor.execute(operationToPerform, name, this);
    }

    public Conditions addDeviceToScene(String sceneName, String deviceName, Conditions condition) {
        CrudOperation getByName = new GetByNameOperationImpl();
        Scene scene = (Scene) operationExecutor.execute(getByName, sceneName, this);
        Device device = (Device) operationExecutor.execute(getByName, deviceName, "Device");
        if(Objects.isNull(scene) || Objects.isNull(device))
            return null;
        condition.setScene(scene);
        condition.setDevice(device);
        //TODO: activation date is always null (Postman return null date format)
        //condition.setActivation();
        Conditions conditionToAdd = conditionsService.findConditionsByName(condition.getName());
        if(Objects.nonNull(conditionToAdd))
        {
            return null;
        }
        conditionToAdd = conditionsService.addConditions(condition);
        scene.addCondition(conditionToAdd);
        device.addCondition(conditionToAdd);
        return conditionToAdd;
    }

    public Integer removeDeviceToScene(String sceneName, String deviceName) {
        CrudOperation getByName = new GetByNameOperationImpl();
        Scene scene = (Scene) operationExecutor.execute(getByName, sceneName, this);
        List<Conditions> conditions = (List<Conditions>) operationExecutor.execute(new GetConditionsByDeviceName(), deviceName, conditionsService);
        if(Objects.isNull(scene) || Objects.isNull(conditions))
            return 0;
        for (Conditions condition: conditions) {
            conditionsService.deleteConditions(condition.getName());
        }
        return 1;
    }

    public List<Device> findDevicesInScene(String sceneName) {
        List<Device> devices = new ArrayList<>();
        Scene scene = (Scene) operationExecutor.execute(new GetByNameOperationImpl(), sceneName, this);
        if(Objects.nonNull(scene)) {
            List<Conditions> conditions = scene.getConditions();
            if (Objects.isNull(conditions)) {
                return devices;
            }
            for (Conditions condition : conditions) {
                Device device = condition.getDevice();
                if (Objects.nonNull(device))
                    devices.add(device);
            }
        }
        return devices;
    }




}
