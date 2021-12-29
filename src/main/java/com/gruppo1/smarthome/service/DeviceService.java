package com.gruppo1.smarthome.service;

import com.gruppo1.smarthome.command.api.CrudOperation;
import com.gruppo1.smarthome.command.impl.*;
import com.gruppo1.smarthome.memento.MementoCareTaker;
import com.gruppo1.smarthome.model.*;
import com.gruppo1.smarthome.repository.ConditionRepo;
import com.gruppo1.smarthome.repository.DeviceRepo;
import com.gruppo1.smarthome.repository.RoomRepo;
import com.gruppo1.smarthome.repository.SceneRepo;
import com.gruppo1.smarthome.strategy.ConverterFromJsonToDevice;
import com.gruppo1.smarthome.strategy.FactoryConverterFromJsonToDevice;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class DeviceService {
    private final MementoCareTaker mementoCareTaker;
    private final ConditionService conditionService;

    private final DeviceRepo deviceRepo;
    private final SceneRepo sceneRepo;
    private final ConditionRepo conditionRepo;
    private final RoomRepo roomRepo;

    @Autowired
    public DeviceService(MementoCareTaker mementoCareTaker, ConditionService conditionService, DeviceRepo deviceRepo, SceneRepo sceneRepo, ConditionRepo conditionRepo, RoomRepo roomRepo) {
        this.mementoCareTaker = mementoCareTaker;
        this.conditionService = conditionService;
        this.deviceRepo = deviceRepo;
        this.sceneRepo = sceneRepo;
        this.conditionRepo = conditionRepo;
        this.roomRepo = roomRepo;
    }

    public Device addDevice(JSONObject deviceJson) throws JSONException {
        CrudOperation operationToPerform = new AddOperationImpl(deviceRepo);
        if (!validateJson(deviceJson))
            return null;

        String typeDevice = deviceJson.get("type").toString().toLowerCase();
        Device newDevice = DeviceFactory.create(typeDevice);
        if (isPresent(newDevice)) {
            ConverterFromJsonToDevice converter = FactoryConverterFromJsonToDevice.getInstance(typeDevice);
            converter.convert(deviceJson, newDevice);
            Room room = validateRoom(deviceJson);
            newDevice.setRoom(room);
            mementoCareTaker.push(operationToPerform, newDevice.createMemento());
            return operationToPerform.execute(newDevice);

        }
        return null;
    }

    public List<Device> findAllDevices() {
        CrudOperation operationToPerform = new GetOperationImpl(deviceRepo);
        mementoCareTaker.push(operationToPerform, null);
        return (List<Device>) (List<?>) operationToPerform.execute();
    }

    public Device findDeviceByName(String name) {
        CrudOperation operationToPerform = new GetByNameOperationImpl(deviceRepo);
        mementoCareTaker.push(operationToPerform, null); //TODO SS
        return operationToPerform.execute(name);
    }

    public Device updateDevice(String deviceNameToUpdate, JSONObject deviceJson) throws JSONException {
        CrudOperation operationToPerform = new UpdateOperationImpl(deviceRepo);
        CrudOperation getByNameOperation = new GetByNameOperationImpl(deviceRepo);

        Device oldDevice = getByNameOperation.execute(deviceNameToUpdate);
        if (validateUpdate(deviceJson, oldDevice)) {
            mementoCareTaker.push(operationToPerform, oldDevice.createMemento());
            ConverterFromJsonToDevice converter = FactoryConverterFromJsonToDevice.getInstance(oldDevice.getType().toLowerCase());
            converter.convert(deviceJson, oldDevice); // Aggiorna i valori
            Room room = validateRoom(deviceJson); // Setta la room
            oldDevice.setRoom(room);
            return operationToPerform.execute(oldDevice);
        }
        return null;
    }

    public int deleteDevice(String name) {
        CrudOperation getByNameOperation = new GetByNameOperationImpl(deviceRepo);
        CrudOperation operationToPerform = new DeleteOperationImpl(deviceRepo);

        Device device = getByNameOperation.execute(name);
        if (Objects.nonNull(device)) {
            mementoCareTaker.push(operationToPerform, device.createMemento());
            return operationToPerform.execute(device);
        }
        return 0;
    }

    public Condition addConditionByDeviceName(String deviceName, String sceneName, Condition condition) {
        CrudOperation getDevicesByNameOperation = new GetByNameOperationImpl(deviceRepo);
        CrudOperation getScenesByNameOperation = new GetByNameOperationImpl(sceneRepo);
        Device device = getDevicesByNameOperation.execute(deviceName);
        Scene scene = getScenesByNameOperation.execute(sceneName);
        Condition conditionToAdd = conditionService.findConditionByName(condition.getName());
        if (Objects.nonNull(device) && Objects.nonNull(scene) && !isPresent(conditionToAdd)) {
            condition.setDevice((device));
            condition.setScene(scene);
            return conditionService.addCondition(condition);
        }
        return null;
    }

    public List<Condition> findConditionsInDevice(String deviceName) {
        CrudOperation operationToPerform = new GetByNameOperationImpl(deviceRepo);
        List<Condition> conditions = new ArrayList<>();
        Device device = operationToPerform.execute(deviceName);
        if (isPresent(device))
            conditions = device.getConditions();

        return conditions;
    }

    public Integer deleteConditionsInDevice(String deviceName, String conditionName) {
        CrudOperation operationToPerform  = new GetByNameOperationImpl(deviceRepo);
        Device device = operationToPerform.execute(deviceName);
        Condition condition = conditionService.findConditionByName(conditionName);
        if (isPresent(device) && isPresent(condition)) {
            if (device.equals(condition.getDevice()))
                return conditionService.deleteCondition(conditionName);
        }
        return 0;
    }

    private Boolean isPresent(SmartHomeItem item) {
        return Objects.nonNull(item);
    }

    private Boolean validateJson(JSONObject objectToCheck) throws JSONException {
        CrudOperation getOperation = new GetByNameOperationImpl(deviceRepo);
        return objectToCheck.has("type") && objectToCheck.has("name") && Objects.isNull(getOperation.execute(objectToCheck.get("name").toString()));
    }

    private Room validateRoom(JSONObject deviceJson) throws JSONException {
        CrudOperation operationToPerform = new GetByNameOperationImpl(roomRepo);
        Room room;
        if (deviceJson.has("room_name")) {
            room = operationToPerform.execute(deviceJson.get("room_name").toString());
            if (!isPresent(room)) {
                room = operationToPerform.execute("Default");
            }
        } else {
            room = operationToPerform.execute("Default");
        }
        return room;
    }

    private Boolean validateUpdate(JSONObject deviceJson, Device oldDevice) throws JSONException {
        if (!isPresent(oldDevice))
            return false;
        if (deviceJson.has("type") && !deviceJson.get("type").toString().equalsIgnoreCase(oldDevice.getType()))
            return false;
        if (deviceJson.has("name")) {
            CrudOperation operationToPerform = new GetByNameOperationImpl(deviceRepo);
            Device deviceDB = operationToPerform.execute(deviceJson.get("name").toString());
            return !isPresent(deviceDB) || deviceDB.getName().equalsIgnoreCase(oldDevice.getName());
        }
        return true;
    }
}