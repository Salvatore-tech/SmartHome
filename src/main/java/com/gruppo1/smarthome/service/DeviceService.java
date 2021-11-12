package com.gruppo1.smarthome.service;

import com.gruppo1.smarthome.crud.api.CrudOperation;
import com.gruppo1.smarthome.crud.beans.CrudOperationExecutor;
import com.gruppo1.smarthome.crud.impl.*;
import com.gruppo1.smarthome.crud.memento.Memento;
import com.gruppo1.smarthome.crud.memento.MementoCareTaker;
import com.gruppo1.smarthome.model.Device;
import com.gruppo1.smarthome.model.FactoryDevice;
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
    private final CrudOperationExecutor operationExecutor;
    private final MementoCareTaker mementoCareTaker;

    @Autowired
    public DeviceService(CrudOperationExecutor operationExecutor, MementoCareTaker mementoCareTaker) {
        this.operationExecutor = operationExecutor;
        this.mementoCareTaker = mementoCareTaker;
    }

    public Device addDevice(JSONObject device) throws JSONException {
        if(Objects.nonNull(findDeviceByName(device.get("name").toString())))
            return null;
        FactoryDevice factory = new FactoryDevice();
        Device newDevice = factory.getDevice(device.get("type").toString().toLowerCase());
        newDevice.setName(device.get("name").toString());
        newDevice.setStatus(Boolean.parseBoolean(device.get("status").toString()));
        newDevice.setType(device.get("type").toString());
        newDevice.setRoom(null);
        CrudOperation operationToPerform = new AddOperationImpl();
        mementoCareTaker.add(new Memento(operationToPerform, newDevice, "Add device"));
        return (Device) operationExecutor.execute(operationToPerform, newDevice);
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

    public Device updateDevice(String deviceNameToUpdate, Device updatedDevice) {

        //TODO: hide more the id handling
        Device oldDevice = (Device) operationExecutor.execute(new GetByNameOperationImpl(), deviceNameToUpdate, this);
        if (Objects.nonNull(oldDevice)) {
            updatedDevice.setId(oldDevice.getId());
            UpdateOperationImpl operationToPerform = new UpdateOperationImpl();
            mementoCareTaker.add(new Memento(operationToPerform, oldDevice, "Update device"));
            return (Device) operationExecutor.execute(operationToPerform, updatedDevice);
        }
        return null;
    }

    public Integer deleteDevice(String name) {
        Device device = (Device) operationExecutor.execute(new GetByNameOperationImpl(), name, this);
        if (Objects.nonNull(device)) {
            Device deviceToDelete = (Device) operationExecutor.execute(new GetByNameOperationImpl(), name, this);
            CrudOperation operationToPerform = new DeleteOperationImpl();
            mementoCareTaker.add(new Memento(operationToPerform, deviceToDelete, "Delete device"));
            return (Integer) operationExecutor.execute(operationToPerform, name, this);
        }
        return 0;
    }


    public Integer countDevices() {
        CrudOperation operationToPerform = new GetOperationImpl();
        mementoCareTaker.add(new Memento(operationToPerform, new Device("Devices"), "Count devices")); //TODO
        return ((List<Device>) operationExecutor.execute(operationToPerform, null)).size();
    }

}
