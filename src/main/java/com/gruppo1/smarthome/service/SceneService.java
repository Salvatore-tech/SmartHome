package com.gruppo1.smarthome.service;

import com.gruppo1.smarthome.beans.CrudOperationExecutor;
import com.gruppo1.smarthome.command.api.CrudOperation;
import com.gruppo1.smarthome.command.impl.*;
import com.gruppo1.smarthome.memento.Memento;
import com.gruppo1.smarthome.memento.MementoCareTaker;
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
import java.util.concurrent.atomic.AtomicInteger;

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
        CrudOperation operationToPerform = new AddOperationImpl();
        if (!isPresent((Scene) operationExecutor.execute(new GetByNameOperationImpl(), scene.getName(), this))) {
            mementoCareTaker.add(new Memento(operationToPerform, scene, "Add a scene"));
            return (Scene) operationExecutor.execute(operationToPerform, scene);
        }
        return null;
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
        CrudOperation operationToPerform = new UpdateOperationImpl();
        Scene oldScene = (Scene) operationExecutor.execute(new GetByNameOperationImpl(), sceneNameToUpdate, this);
        if (validateUpdate(oldScene, updatedScene)) {
            setScene(oldScene, updatedScene);
            mementoCareTaker.add(new Memento(operationToPerform, oldScene, "Update a scene"));
            return (Scene) operationExecutor.execute(operationToPerform, oldScene);
        }
        return null;
    }

    public Integer deleteScene(String sceneName) {
        CrudOperation operationToPerform = new DeleteOperationImpl();
        Scene scene = (Scene) operationExecutor.execute(new GetByNameOperationImpl(), sceneName, this);
        if (isPresent(scene)) {
            mementoCareTaker.add(new Memento(operationToPerform, scene, "Delete a scene"));
            return (Integer) operationExecutor.execute(operationToPerform, sceneName, this);
        }
        return 0;
    }

    public Condition addDeviceToScene(String sceneName, String deviceName, Condition condition) {
        CrudOperation operationToPerform = new GetByNameOperationImpl();
        if (isPresent((Condition) operationExecutor.execute(operationToPerform, condition.getName(), "Condition")))
            return null;
        Scene scene = (Scene) operationExecutor.execute(operationToPerform, sceneName, this);
        Device device = (Device) operationExecutor.execute(operationToPerform, deviceName, "Device");
        Condition conditionToAdd = conditionService.findConditionsByName(condition.getName());
        if (!isPresent(scene) || !isPresent(device) || isPresent(conditionToAdd))
            return null;
        condition.setScene(scene);
        condition.setDevice(device);
        conditionToAdd = conditionService.addCondition(condition);
        scene.addCondition(conditionToAdd);
        device.addCondition(conditionToAdd);
        return conditionToAdd;
    }

    public Integer removeDeviceToScene(String sceneName, String deviceName) {
        AtomicInteger conditionsDeleted = new AtomicInteger(0);
        Scene scene = (Scene) operationExecutor.execute(new GetByNameOperationImpl(), sceneName, this);
        List<Condition> conditions = (List<Condition>) operationExecutor.execute(new GetConditionsByDeviceName(), deviceName, "Condition");
        if (!isPresent(scene) || Objects.isNull(conditions))
            return 0;
        conditions.forEach(condition -> {
            if (condition.getScene().equals(scene)) {
                conditionService.deleteCondition(condition.getName());
                conditionsDeleted.incrementAndGet();
            }
        });
        return (conditionsDeleted.get() > 0) ? 1 : 0;
    }

    public List<Device> findDevicesInScene(String sceneName) {
        List<Device> devices = new ArrayList<>();
        Scene scene = (Scene) operationExecutor.execute(new GetByNameOperationImpl(), sceneName, this);
        if (isPresent(scene)) {
            List<Condition> conditions = scene.getConditions();
            if (Objects.isNull(conditions)) {
                return devices;
            }
            conditions.forEach(condition ->
                    {
                        Device device = condition.getDevice();
                        if (isPresent(device))
                            devices.add(device);
                    }

            );
        }
        return devices;
    }

    private Boolean isPresent(SmartHomeItem item) {
        return Objects.nonNull(item);
    }

    public List<Condition> findConditionsInScene(String sceneName) {
        CrudOperation operationToPerform = new GetByNameOperationImpl();
        Scene scene = (Scene) operationExecutor.execute(operationToPerform, sceneName, this);
        if (isPresent(scene)) {
            List<Condition> conditions = scene.getConditions();
            mementoCareTaker.add(new Memento(operationToPerform, new Condition("Conditions"), "Find conditions"));
            return conditions;
        }
        return null;
    }

    public Integer deleteConditionsInScene(String sceneName, String conditionName) {
        CrudOperation operationToPerform = new DeleteOperationImpl();
        Scene scene = (Scene) operationExecutor.execute(new GetByNameOperationImpl(), sceneName, this);
        Condition condition = conditionService.findConditionsByName(conditionName);
        if (isPresent(scene) && isPresent(condition)) {
            if (scene.equals(condition.getScene())) {
                mementoCareTaker.add(new Memento(operationToPerform, condition, "Delete condition"));
                return conditionService.deleteCondition(conditionName);
            }
        }
        return 0;
    }

    private Boolean validateUpdate(Scene oldScene, Scene updatedScene) {
        if (!isPresent(oldScene)) // No scene to update
            return false;
        Scene persistentScene = (Scene) operationExecutor.execute(new GetByNameOperationImpl(), updatedScene.getName(), this);
        return !isPresent(persistentScene) || persistentScene.getName().equalsIgnoreCase(updatedScene.getName()); // Check if the new name violates unique constraint
    }

    private void setScene(Scene oldScene, Scene updatedScene) {
        oldScene.setName(updatedScene.getName());
        oldScene.setStatus(updatedScene.getStatus());
    }
}
