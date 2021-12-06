package com.gruppo1.smarthome.service;

import com.gruppo1.smarthome.beans.DeviceFactory;
import com.gruppo1.smarthome.command.api.CrudOperation;
import com.gruppo1.smarthome.command.impl.*;
import com.gruppo1.smarthome.memento.MementoCareTaker;
import com.gruppo1.smarthome.model.Condition;
import com.gruppo1.smarthome.model.Room;
import com.gruppo1.smarthome.model.Scene;
import com.gruppo1.smarthome.model.SmartHomeItem;
import com.gruppo1.smarthome.model.device.Device;
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
    private final DeviceFactory deviceFactory;
    private final MementoCareTaker mementoCareTaker;
    private final ConditionService conditionService;

    private final DeviceRepo deviceRepo;
    private final SceneRepo sceneRepo;
    private final ConditionRepo conditionRepo;
    private final RoomRepo roomRepo;

    @Autowired
    public DeviceService(DeviceFactory deviceFactory, MementoCareTaker mementoCareTaker, ConditionService conditionService, DeviceRepo deviceRepo, SceneRepo sceneRepo, ConditionRepo conditionRepo, RoomRepo roomRepo) {
        this.deviceFactory = deviceFactory;
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
        Device newDevice = deviceFactory.create(typeDevice);
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
        return (Device) operationToPerform.execute(name);
    }

    public Device updateDevice(String deviceNameToUpdate, JSONObject deviceJson) throws JSONException {
        CrudOperation operationToPerform = new UpdateOperationImpl(deviceRepo);
        CrudOperation getByNameOperation = new GetByNameOperationImpl(deviceRepo);

        Device oldDevice = (Device) getByNameOperation.execute(deviceNameToUpdate);
        if (validateUpdate(deviceJson, oldDevice)) {
            ConverterFromJsonToDevice converter = FactoryConverterFromJsonToDevice.getInstance(oldDevice.getType().toLowerCase());
            converter.convert(deviceJson, oldDevice);
            Room room = validateRoom(deviceJson);
            mementoCareTaker.push(operationToPerform, oldDevice.createMemento()); // TODO SS
            oldDevice.setRoom(room);
            return (Device) operationToPerform.execute(oldDevice);
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
        List<SmartHomeItem> device = getDevicesByNameOperation.execute(deviceName);
        List<SmartHomeItem> scene = getScenesByNameOperation.execute(sceneName);
        Condition conditionToAdd = conditionService.findConditionsByName(condition.getName());
        if (!device.isEmpty() && !scene.isEmpty() && !isPresent(conditionToAdd)) {
            condition.setDevice((Device) device.get(0));
            condition.setScene((Scene) scene.get(0));
            return conditionService.addCondition(condition);
        }
        return null;
    }

    //TODO: TO FIX
    public List<Condition> findConditionsInDevice(String deviceName) {
        CrudOperation operationToPerform = new GetByNameOperationImpl(conditionRepo);
        List<Condition> conditions = new ArrayList<>();
        Device device = (Device) operationToPerform.execute(deviceName);
        if (isPresent(device))
            conditions = device.getConditions();

        return conditions;
    }

    public Condition deleteConditionsInDevice(String deviceName, String conditionName) {
        CrudOperation getConditionsByDeviceNameOperation = new GetByNameOperationImpl(conditionRepo);


        Device device = (Device) getConditionsByDeviceNameOperation.execute(deviceName);
//        Device device = (Device) operationExecutor.execute(new GetByNameOperationImpl(deviceRepo), deviceName, this);
        Condition condition = conditionService.findConditionsByName(conditionName);
        if (isPresent(device) && isPresent(condition)) {
            if (device.equals(condition.getDevice()))
                return conditionService.deleteCondition(conditionName);
        }
        return null;
    }

    private Boolean isPresent(SmartHomeItem item) {
        return Objects.nonNull(item);
    }

    private Boolean validateJson(JSONObject objectToCheck) {
        return objectToCheck.has("type") && objectToCheck.has("name");
    }

    private Room validateRoom(JSONObject deviceJson) throws JSONException {
        CrudOperation operationToPerform = new GetByNameOperationImpl(roomRepo);
        Room room;
        if (deviceJson.has("room_name")) {
            room = (Room) operationToPerform.execute(deviceJson.get("room_name").toString());
            if (!isPresent(room)) {
                room = (Room) operationToPerform.execute("Default");
            }
        } else {
            room = (Room) operationToPerform.execute("Default");
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
            Device deviceDB = (Device) operationToPerform.execute(deviceJson.get("name").toString());
            return !isPresent(deviceDB) || deviceDB.getName().equalsIgnoreCase(oldDevice.getName());
        }
        return true;
    }
}