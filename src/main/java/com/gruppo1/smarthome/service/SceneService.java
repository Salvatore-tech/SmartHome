package com.gruppo1.smarthome.service;

import com.gruppo1.smarthome.crud.api.CrudOperation;
import com.gruppo1.smarthome.crud.beans.CrudOperationExecutor;
import com.gruppo1.smarthome.crud.impl.*;
import com.gruppo1.smarthome.crud.memento.Memento;
import com.gruppo1.smarthome.crud.memento.MementoCareTaker;
import com.gruppo1.smarthome.model.Condition;
import com.gruppo1.smarthome.model.Device;
import com.gruppo1.smarthome.model.Scene;
import com.gruppo1.smarthome.model.SmartHomeItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@Transactional
public class SceneService {
    private final ConditionService conditionService;
    private final CrudOperationExecutor operationExecutor;
    private final MementoCareTaker mementoCareTaker;

    @Autowired
    public SceneService(ConditionService conditionService, CrudOperationExecutor operationExecutor, MementoCareTaker mementoCareTaker) {
        this.conditionService = conditionService;
        this.operationExecutor = operationExecutor;
        this.mementoCareTaker = mementoCareTaker;
    }

    public Scene addScene(Scene scene) {
        Scene sceneToCheck = (Scene) operationExecutor.execute(new GetByNameOperationImpl(), scene.getName(), this);
        if(Objects.nonNull(sceneToCheck))
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
        //TODO SS: hide more the id handling
        CrudOperation getByName = new GetByNameOperationImpl();
        Scene oldScene = (Scene) operationExecutor.execute(getByName, sceneNameToUpdate, this);
        Scene sceneToCheck = (Scene) operationExecutor.execute(getByName, updatedScene.getName(),this);
        if (Objects.nonNull(oldScene) && Objects.isNull(sceneToCheck)) {
            setScene(oldScene, updatedScene);
            UpdateOperationImpl operationToPerform = new UpdateOperationImpl();
            mementoCareTaker.add(new Memento(operationToPerform, oldScene, "Update a scene"));
            return (Scene) operationExecutor.execute(operationToPerform, oldScene);
        }
        return null;
    }

    public Integer deleteScene(String name) {
        SmartHomeItem sceneToDelete = (SmartHomeItem) operationExecutor.execute(new GetByNameOperationImpl(), name, this);
        if (Objects.nonNull(sceneToDelete)) {
            DeleteOperationImpl operationToPerform = new DeleteOperationImpl();
            mementoCareTaker.add(new Memento(operationToPerform, sceneToDelete, "Delete a scene")); //TODO
            return (Integer) operationExecutor.execute(operationToPerform, name, this);
        }
        return 0;
    }

    public Condition addDeviceToScene(String sceneName, String deviceName, Condition condition) {
        CrudOperation getByName = new GetByNameOperationImpl();
        Condition conditionToCheck = (Condition) operationExecutor.execute(getByName, condition.getName(), "Condition");
        if(Objects.nonNull(conditionToCheck))
            return null;
        Scene scene = (Scene) operationExecutor.execute(getByName, sceneName, this);
        Device device = (Device) operationExecutor.execute(getByName, deviceName, "Device");
        if (Objects.isNull(scene) || Objects.isNull(device))
            return null;
        condition.setScene(scene);
        condition.setDevice(device);
        //TODO: activation date is always null (Postman return null date format)
        //condition.setActivation();
        Condition conditionToAdd = conditionService.findConditionsByName(condition.getName());
        if (Objects.nonNull(conditionToAdd)) {
            return null;
        }
        conditionToAdd = conditionService.addCondition(condition);
        scene.addCondition(conditionToAdd);
        device.addCondition(conditionToAdd);
        return conditionToAdd;
    }

    public Integer removeDeviceToScene(String sceneName, String deviceName) {
        AtomicBoolean conditionFound = new AtomicBoolean(false);
        CrudOperation getByName = new GetByNameOperationImpl();
        Scene scene = (Scene) operationExecutor.execute(getByName, sceneName, this);
        List<Condition> conditions = (List<Condition>) operationExecutor.execute(new GetConditionsByDeviceName(), deviceName, "Condition");
        if (Objects.isNull(scene) || Objects.isNull(conditions))
            return 0;
        conditions.forEach(condition -> {
            if (condition.getScene().getName().equals(sceneName)) {
                conditionService.deleteCondition(condition.getName());
                conditionFound.set(true);
            }
        });
        return (conditionFound.get() == true) ? 1 : 0;
    }

    public List<Device> findDevicesInScene(String sceneName) {
        List<Device> devices = new ArrayList<>();
        Scene scene = (Scene) operationExecutor.execute(new GetByNameOperationImpl(), sceneName, this);
        if (Objects.nonNull(scene)) {
            List<Condition> conditions = scene.getConditions();
            if (Objects.isNull(conditions)) {
                return devices;
            }
            conditions.forEach(condition ->
                    {
                        Device device = condition.getDevice();
                        if (Objects.nonNull(device))
                            devices.add(device);
                    }

            );
        }
        return devices;
    }

    private void setScene(Scene oldScene, Scene updatedScene) {
        oldScene.setName(updatedScene.getName());
        oldScene.setStatus(updatedScene.getStatus());
    }
}
