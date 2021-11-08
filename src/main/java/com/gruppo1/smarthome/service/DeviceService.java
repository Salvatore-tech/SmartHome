package com.gruppo1.smarthome.service;

import com.gruppo1.smarthome.crud.api.CrudOperation;
import com.gruppo1.smarthome.crud.api.SmartHomeItemLight;
import com.gruppo1.smarthome.crud.beans.CrudOperationExecutor;
import com.gruppo1.smarthome.crud.impl.*;
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
        //TODO check if already exists

        FactoryDevice factory = new FactoryDevice();
        Device newDevice = factory.getDevice(device.get("type").toString().toLowerCase());
        newDevice.setName(device.get("name").toString());
        newDevice.setStatus(Boolean.parseBoolean(device.get("status").toString()));
        newDevice.setType(device.get("type").toString());
        newDevice.setRoom(null);
        CrudOperation operationToPerform = new AddOperationImpl();
        mementoCareTaker.add(operationToPerform.generateMemento(), new SmartHomeItemLight(newDevice.getName(), newDevice.getType()));
        return (Device) operationExecutor.execute(operationToPerform, newDevice);
    }

    public List<Device> findAllDevices() {
        CrudOperation operationToPerform = new GetOperationImpl();
        mementoCareTaker.add(operationToPerform.generateMemento(), new SmartHomeItemLight(null, "Device"));
        return (List<Device>) operationExecutor.execute(operationToPerform, this);
    }

    public Device findDeviceByName(String name) {
        CrudOperation operationToPerform = new GetByNameOperationImpl();
        mementoCareTaker.add(operationToPerform.generateMemento(), new SmartHomeItemLight(name, "Device")); //TODO
        return (Device) operationExecutor.execute(operationToPerform, name, this);
    }

    public Device updateDevice(String deviceNameToUpdate, Device updatedDevice) {
//        if (deviceRepo.findByName(device.getName()).isPresent()) {
//            deviceRepo.save(device);
//            return device;
//        }
        //TODO: hide more the id handling

        Device oldDevice = (Device) operationExecutor.execute(new GetByNameOperationImpl(), deviceNameToUpdate, this);
        if (Objects.nonNull(oldDevice)) {
            updatedDevice.setId(oldDevice.getId());
            UpdateOperationImpl operationToPerform = new UpdateOperationImpl();
            mementoCareTaker.add(operationToPerform.generateMemento(), new SmartHomeItemLight(oldDevice.getName(), oldDevice.getType())); //TODO
            return (Device) operationExecutor.execute(operationToPerform, updatedDevice);
        }
        return null;
    }

    public Integer deleteDevice(String name) {

        Device device = (Device) operationExecutor.execute (new GetByNameOperationImpl(), name, this);
        if (Objects.nonNull(device))
        {
            return (Integer) operationExecutor.execute(new DeleteOperationImpl(), name, this);
        }

        return 0;
    }


    public Integer countDevices() {
        CrudOperation operationToPerform = new GetOperationImpl();
        mementoCareTaker.add(operationToPerform.generateMemento(), null); //TODO
        return ((List<Device>) operationExecutor.execute(operationToPerform, new SmartHomeItemLight(null, "Devices"))).size();
    }

}
