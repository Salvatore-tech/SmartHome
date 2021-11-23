package com.gruppo1.smarthome.service;

import com.gruppo1.smarthome.crud.api.CrudOperation;
import com.gruppo1.smarthome.crud.beans.CrudOperationExecutor;
import com.gruppo1.smarthome.crud.impl.*;
import com.gruppo1.smarthome.crud.memento.Memento;
import com.gruppo1.smarthome.crud.memento.MementoCareTaker;
import com.gruppo1.smarthome.model.*;
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
    private CrudOperation operationToPerform;

    @Autowired
    public SceneService(ConditionService conditionService, CrudOperationExecutor operationExecutor, MementoCareTaker mementoCareTaker) {
        this.conditionService = conditionService;
        this.operationExecutor = operationExecutor;
        this.mementoCareTaker = mementoCareTaker;
    }

    public Scene addScene(Scene scene) {
        operationToPerform = new GetByNameOperationImpl();
        if (!isPresent((Scene) operationExecutor.execute(operationToPerform, scene.getName(), this))) {
            operationToPerform = new AddOperationImpl();
            mementoCareTaker.add(new Memento(operationToPerform, scene, "Add a scene"));
            return (Scene) operationExecutor.execute(operationToPerform, scene);
        }
        return null;
    }

    public List<Scene> findAllScene() {
        operationToPerform = new GetOperationImpl();
        mementoCareTaker.add(new Memento(operationToPerform, null, "Get all scenes"));
        return (List<Scene>) operationExecutor.execute(operationToPerform, this);
    }

    public Scene findSceneByName(String name) {
        operationToPerform = new GetByNameOperationImpl();
        Scene result = (Scene) operationExecutor.execute(operationToPerform, name, this);
        mementoCareTaker.add(new Memento(operationToPerform, result, "Find a scene by name"));
        return result;
    }

    public Scene updateScene(String sceneNameToUpdate, Scene updatedScene) {
        operationToPerform = new GetByNameOperationImpl();
        Scene oldScene = (Scene) operationExecutor.execute(operationToPerform, sceneNameToUpdate, this);
        if (validateUpdate(oldScene, updatedScene)) {
            setScene(oldScene, updatedScene);
            operationToPerform = new UpdateOperationImpl();
            mementoCareTaker.add(new Memento(operationToPerform, oldScene, "Update a scene"));
            return (Scene) operationExecutor.execute(operationToPerform, oldScene);
        }
        return null;
    }

    public Integer deleteScene(String sceneName) {
        operationToPerform = new GetByNameOperationImpl();
        Scene scene = (Scene) operationExecutor.execute(operationToPerform, sceneName, this);
        if (isPresent(scene)) {
            operationToPerform = new DeleteOperationImpl();
            mementoCareTaker.add(new Memento(operationToPerform, scene, "Delete a scene")); //TODO
            return (Integer) operationExecutor.execute(operationToPerform, sceneName, this);
        }
        return 0;
    }

    public Condition addDeviceToScene(String sceneName, String deviceName, Condition condition) {
        operationToPerform = new GetByNameOperationImpl();
        if (isPresent((Condition) operationExecutor.execute(operationToPerform, condition.getName(), "Condition")))
            return null;
        Scene scene = (Scene) operationExecutor.execute(operationToPerform, sceneName, this);
        Device device = (Device) operationExecutor.execute(operationToPerform, deviceName, "Device");
        Condition conditionToAdd = conditionService.findConditionsByName(condition.getName());
        if (!isPresent(scene) || !isPresent(device) || isPresent(conditionToAdd))
            return null;
        condition.setScene(scene);
        condition.setDevice(device);
        //TODO: activation date is always null (Postman return null date format)
        //condition.setActivation();
        conditionToAdd = conditionService.addCondition(condition);
        scene.addCondition(conditionToAdd);
        device.addCondition(conditionToAdd);
        return conditionToAdd;
    }

    public Integer removeDeviceToScene(String sceneName, String deviceName) {
        AtomicBoolean conditionFound = new AtomicBoolean(false);
        operationToPerform = new GetByNameOperationImpl();
        Scene scene = (Scene) operationExecutor.execute(operationToPerform, sceneName, this);
        List<Condition> conditions = (List<Condition>) operationExecutor.execute(new GetConditionsByDeviceName(), deviceName, "Condition");
        if (!isPresent(scene) || Objects.isNull(conditions))
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

    private Boolean isPresent (SmartHomeItem item) {
        return Objects.nonNull(item) ? true : false;
    }

    private Boolean validateUpdate(Scene oldScene, Scene updatedScene) {
        if (isPresent(oldScene)) {
            operationToPerform = new GetByNameOperationImpl();
            Scene sceneToCheck = (Scene) operationExecutor.execute(operationToPerform, updatedScene.getName(), this);
            if (!isPresent(sceneToCheck)) {
                return true;
            }
        }
        return false;
    }

    private void setScene(Scene oldScene, Scene updatedScene) {
        oldScene.setName(updatedScene.getName());
        oldScene.setStatus(updatedScene.getStatus());
    }
}
