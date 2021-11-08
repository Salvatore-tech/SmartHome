package com.gruppo1.smarthome.service;

import com.gruppo1.smarthome.crud.beans.CrudOperationExecutor;
import com.gruppo1.smarthome.crud.impl.*;
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

    @Autowired
    public DeviceService(CrudOperationExecutor operationExecutor) {
        this.operationExecutor = operationExecutor;
    }

    public Device addDevice(JSONObject device) throws JSONException {
        //TODO check if already exists

        FactoryDevice factory = new FactoryDevice();
        Device newDevice = factory.getDevice(device.get("type").toString().toLowerCase());
        newDevice.setName(device.get("name").toString());
        newDevice.setStatus(Boolean.parseBoolean(device.get("status").toString()));
        newDevice.setType(device.get("type").toString());
        newDevice.setRoom(null);
        return (Device) operationExecutor.execute(new AddOperationImpl(), newDevice);
    }

    public List<Device> findAllDevices() {
        return (List<Device>) operationExecutor.execute(new GetOperationImpl(), this);
    }

    public Device findDeviceByName(String name) {
        return (Device) operationExecutor.execute(new GetByNameOperationImpl(), name, this);
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
            return (Device) operationExecutor.execute(new UpdateOperationImpl(), updatedDevice);
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
        return ((List<Device>) operationExecutor.execute(new GetOperationImpl(), this)).size();
    }

}
