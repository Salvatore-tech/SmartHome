package com.gruppo1.smarthome.service;

import com.gruppo1.smarthome.adapter.FactoryDeviceAdapter;
import com.gruppo1.smarthome.beans.CrudOperationExecutor;
import com.gruppo1.smarthome.beans.DeviceFactory;
import com.gruppo1.smarthome.command.api.CrudOperation;
import com.gruppo1.smarthome.command.impl.*;
import com.gruppo1.smarthome.memento.Memento;
import com.gruppo1.smarthome.memento.MementoCareTaker;
import com.gruppo1.smarthome.model.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class DeviceService {
    private final DeviceFactory deviceFactory;
    private final FactoryDeviceAdapter adapterDevice;
    private final CrudOperationExecutor operationExecutor;
    private final MementoCareTaker mementoCareTaker;

    private final ConditionService conditionService;
    private CrudOperation operationToPerform;


    @Autowired
    public DeviceService(DeviceFactory deviceFactory, FactoryDeviceAdapter adapterDevice, CrudOperationExecutor operationExecutor, MementoCareTaker mementoCareTaker, ConditionService conditionService) {
        this.deviceFactory = deviceFactory;
        this.adapterDevice = adapterDevice;
        this.operationExecutor = operationExecutor;
        this.mementoCareTaker = mementoCareTaker;
        this.conditionService = conditionService;
    }

    public Device addDevice(JSONObject deviceJson) throws JSONException {
        CrudOperation operationToPerform = new AddOperationImpl();
        if (!validateJson(deviceJson))
            return null;

        String typeDevice = deviceJson.get("type").toString().toLowerCase();
        Device newDevice = deviceFactory.create(typeDevice);
        if (isPresent(newDevice)) {
            adapterDevice.adapt(deviceJson, newDevice);
            Room room = validateRoom(deviceJson);
            newDevice.setRoom(room);
            mementoCareTaker.add(new Memento(operationToPerform, newDevice, "Add device"));
            return (Device) operationExecutor.execute(operationToPerform, newDevice);
        }
        return null;
    }

    public List<Device> findAllDevices() {
        CrudOperation operationToPerform = new GetOperationImpl();
        mementoCareTaker.add(new Memento(operationToPerform, new Device("Device"), "Find all devices"));
        return (List<Device>) operationExecutor.execute(operationToPerform, this);
    }

    public Device findDeviceByName(String name) {
        CrudOperation operationToPerform = new GetByNameOperationImpl();
        Device result = (Device) operationExecutor.execute(operationToPerform, name, this);
        mementoCareTaker.add(new Memento(operationToPerform, result, "Find a device given a name"));
        return result;
    }

    public Device updateDevice(String deviceNameToUpdate, JSONObject deviceJson) throws JSONException {
        CrudOperation operationToPerform = new UpdateOperationImpl();
        Device oldDevice = (Device) operationExecutor.execute(new GetByNameOperationImpl(), deviceNameToUpdate, this);
        if (validateUpdate(deviceJson, oldDevice)) {
            adapterDevice.adapt(deviceJson, oldDevice);
            Room room = validateRoom(deviceJson);
            oldDevice.setRoom(room);
            return (Device) operationExecutor.execute(operationToPerform, oldDevice);
        }
        return null;
    }

    public Integer deleteDevice(String name) {
        Device device = (Device) operationExecutor.execute(new GetByNameOperationImpl(), name, this);
        if (isPresent(device)) {
            CrudOperation operationToPerform = new DeleteOperationImpl();
            mementoCareTaker.add(new Memento(operationToPerform, device, "Delete device"));
            return (Integer) operationExecutor.execute(operationToPerform, name, this);
        }
        return 0;
    }

    public Condition addConditionByDeviceName(String deviceName, String sceneName, Condition condition){
        CrudOperation operationToPerform = new GetByNameOperationImpl();
        Device device = (Device) operationExecutor.execute(operationToPerform, deviceName, this);
        Scene scene = (Scene) operationExecutor.execute(operationToPerform, sceneName, "Scene");
        Condition conditionToAdd = conditionService.findConditionsByName(condition.getName());
        if(isPresent(device) && isPresent(scene) && !isPresent(conditionToAdd))
        {
            condition.setDevice(device);
            condition.setScene(scene);
            return conditionService.addCondition(condition);
        }
        return null;
    }

    public List<Condition> findConditionsInDevice(String deviceName) {
        CrudOperation operationToPerform = new GetByNameOperationImpl();
        Device device = (Device) operationExecutor.execute(operationToPerform, deviceName, this);
        if (isPresent(device)) {
            List<Condition> conditions = device.getConditions();
            mementoCareTaker.add(new Memento(operationToPerform, new Condition("Conditions"), "Find conditions"));
            return conditions;
        }
        return null;
    }

    public Integer deleteConditionsInDevice(String deviceName, String conditionName){
        operationToPerform = new DeleteOperationImpl();
        Device device = (Device) operationExecutor.execute(new GetByNameOperationImpl(), deviceName, this);
        Condition condition = conditionService.findConditionsByName(conditionName);
        if (isPresent(device) && isPresent(condition)) {
            if (device.equals(condition.getDevice())) {
                mementoCareTaker.add(new Memento(operationToPerform, condition, "Delete condition"));
                return conditionService.deleteCondition(conditionName);
            }
        }
        return 0;
    }

    private Boolean isPresent(SmartHomeItem item) {
        return Objects.nonNull(item);
    }

    private Boolean validateJson(JSONObject objectToCheck) {
        return objectToCheck.has("type") && objectToCheck.has("name");
    }

    private Room validateRoom(JSONObject deviceJson) throws JSONException {
        CrudOperation operationToPerform = new GetByNameOperationImpl();
        Room room;
        if (deviceJson.has("room_name")) {
            room = (Room) operationExecutor.execute(operationToPerform, deviceJson.get("room_name").toString(), "Room");
            if (!isPresent(room)) {
                room = (Room) operationExecutor.execute(operationToPerform, "Default", "Room");
            }
        } else {
            room = (Room) operationExecutor.execute(operationToPerform, "Default", "Room");
        }
        return room;
    }

    private Boolean validateUpdate(JSONObject deviceJson, Device oldDevice) throws JSONException {
        CrudOperation operationToPerform = new GetByNameOperationImpl();
        if (validateJson(deviceJson)) {
            Device deviceDB = (Device) operationExecutor.execute(operationToPerform, deviceJson.get("name").toString(), this);
            if (isPresent(oldDevice) && (!isPresent(deviceDB) || deviceDB.equals(oldDevice)))
                return deviceJson.get("type").toString().equalsIgnoreCase(oldDevice.getType());
        }
        return false;
    }
}