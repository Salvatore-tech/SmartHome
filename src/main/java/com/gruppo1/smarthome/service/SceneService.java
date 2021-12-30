package com.gruppo1.smarthome.service;

import com.gruppo1.smarthome.command.api.CrudOperation;
import com.gruppo1.smarthome.command.impl.*;
import com.gruppo1.smarthome.memento.MementoCareTaker;
import com.gruppo1.smarthome.model.Condition;
import com.gruppo1.smarthome.model.Device;
import com.gruppo1.smarthome.model.Scene;
import com.gruppo1.smarthome.model.SmartHomeItem;
import com.gruppo1.smarthome.repository.ConditionRepo;
import com.gruppo1.smarthome.repository.DeviceRepo;
import com.gruppo1.smarthome.repository.SceneRepo;
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
    private final MementoCareTaker mementoCareTaker;
    private final SceneRepo sceneRepo;
    private final ConditionRepo conditionRepo;
    private final DeviceRepo deviceRepo;

    @Autowired
    public SceneService(ConditionService conditionService, MementoCareTaker mementoCareTaker, SceneRepo sceneRepo, ConditionRepo conditionRepo, DeviceRepo deviceRepo) {
        this.conditionService = conditionService;
        this.mementoCareTaker = mementoCareTaker;
        this.sceneRepo = sceneRepo;
        this.conditionRepo = conditionRepo;
        this.deviceRepo = deviceRepo;
    }

    public Scene addScene(Scene scene) {
        CrudOperation getSceneOperation = new GetByNameOperationImpl(sceneRepo);
        CrudOperation addSceneOperation = new AddOperationImpl(sceneRepo);
        if (!isPresent(getSceneOperation.execute(scene.getName()))) {
            mementoCareTaker.push(addSceneOperation, scene.createMemento());
            return addSceneOperation.execute(scene);
        }
        return null;
    }

    public List<Scene> findAllScene() {
        CrudOperation operationToPerform = new GetOperationImpl(sceneRepo);
        mementoCareTaker.push(operationToPerform, null);
        return (List<Scene>) (List<?>) operationToPerform.execute();
    }

    public Scene findSceneByName(String name) {
        CrudOperation getSceneOperation = new GetByNameOperationImpl(sceneRepo);
        mementoCareTaker.push(getSceneOperation, null); //TODO SS
        return getSceneOperation.execute(name);
    }

    public Scene updateScene(String sceneNameToUpdate, Scene updatedScene) {
        CrudOperation updateSceneOperation = new UpdateOperationImpl(sceneRepo);
        CrudOperation getSceneOperation = new GetByNameOperationImpl(sceneRepo);
        Scene oldScene = getSceneOperation.execute(sceneNameToUpdate);
        if (validateUpdate(oldScene, updatedScene)) {
            mementoCareTaker.push(updateSceneOperation, oldScene.createMemento());
            setScene(oldScene, updatedScene);
            return updateSceneOperation.execute(oldScene);
        }
        return null;
    }

    public Integer deleteScene(String sceneName) {
        CrudOperation deleteSceneOperation = new DeleteOperationImpl(sceneRepo);
        CrudOperation getSceneOperation = new GetByNameOperationImpl(sceneRepo);
        Scene scene = getSceneOperation.execute(sceneName);
        if (isPresent(scene)) {
            mementoCareTaker.push(deleteSceneOperation, scene.createMemento());
            return deleteSceneOperation.execute(scene);
        }
        return 0;
    }

    public Condition addDeviceToScene(String sceneName, String deviceName, Condition condition) {
        CrudOperation getSceneOperation = new GetByNameOperationImpl(sceneRepo);
        CrudOperation getConditionOperation = new GetByNameOperationImpl(conditionRepo);
        CrudOperation getDeviceOperation = new GetByNameOperationImpl(deviceRepo);
        if (isPresent(getConditionOperation.execute(condition.getName())))
            return null;
        Scene scene = getSceneOperation.execute(sceneName);
        Device device = getDeviceOperation.execute(deviceName);
        Condition conditionToAdd = conditionService.findConditionByName(condition.getName());
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
        CrudOperation getSceneOperation = new GetByNameOperationImpl(sceneRepo);
        CrudOperation getConditions = new GetConditionsByDeviceName(conditionRepo);
        AtomicInteger conditionsDeleted = new AtomicInteger(0);
        Scene scene = getSceneOperation.execute(sceneName);
        List<Condition> conditions = getConditions.execute(deviceName);
        if (!isPresent(scene) || Objects.isNull(conditions))
            return 0;
        conditions.forEach(condition -> {
            if (condition.getScene().equals(scene)) {
                conditionService.deleteCondition(condition.getName());
                conditionsDeleted.incrementAndGet();
            }
        });
        return conditionsDeleted.get();
    }

    public List<Device> findDevicesInScene(String sceneName) {
        CrudOperation getSceneOperation = new GetByNameOperationImpl(sceneRepo);
        List<Device> devices = new ArrayList<>();
        Scene scene = getSceneOperation.execute(sceneName);

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

    public List<Condition> findConditionsInScene(String sceneName) {
        CrudOperation getSceneOperation = new GetByNameOperationImpl(sceneRepo);
        List<Condition> conditions = new ArrayList<>();
        Scene scene = getSceneOperation.execute(sceneName);
        if (isPresent(scene))
            conditions = scene.getConditions();
        return conditions;
    }

    public Integer deleteConditionsInScene(String sceneName, String conditionName) {
        CrudOperation getSceneOperation = new GetByNameOperationImpl(sceneRepo);
        Scene scene = getSceneOperation.execute(sceneName);
        Condition condition = conditionService.findConditionByName(conditionName);
        if (isPresent(scene) && isPresent(condition)) {
            if (scene.equals(condition.getScene()))
                return conditionService.deleteCondition(conditionName);

        }
        return 0;
    }

    private Boolean isPresent(SmartHomeItem item) {
        return Objects.nonNull(item);
    }

    private Boolean validateUpdate(Scene oldScene, Scene updatedScene) {
        if (!isPresent(oldScene)) // No scene to update
            return false;
        CrudOperation getSceneOperation = new GetByNameOperationImpl(sceneRepo);
        Scene persistentScene = getSceneOperation.execute(updatedScene.getName());
        return !isPresent(persistentScene) || persistentScene.getName().equalsIgnoreCase(updatedScene.getName()); // Check if the new name violates unique constraint
    }

    private void setScene(Scene oldScene, Scene updatedScene) {
        oldScene.setName(updatedScene.getName());
        oldScene.setStatus(updatedScene.getStatus());
        oldScene.setPeriod((updatedScene.getPeriod()));
    }
}
