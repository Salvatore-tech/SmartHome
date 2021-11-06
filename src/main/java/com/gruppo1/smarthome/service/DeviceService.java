package com.gruppo1.smarthome.service;

import com.gruppo1.smarthome.crud.beans.CrudOperationExecutor;
import com.gruppo1.smarthome.crud.impl.*;
import com.gruppo1.smarthome.model.Device;
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

    public Device addDevice(Device device) {
//        if (deviceRepo.findByName(device.getName()).isPresent())
//            return null;
////        device.setId(UUID.randomUUID().toString());
//        return deviceRepo.save(device);
        //TODO check if already exists
        return (Device) operationExecutor.execute(new AddOperationImpl(), device);
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
//        Optional<Device> device = deviceRepo.findByName(name);
//        if (device.isPresent()) {
//            deviceRepo.deleteDeviceByName(name);
//            return true;
//        }
//        return false;
        //TODO check if already exists
        return (Integer) operationExecutor.execute(new DeleteOperationImpl(), name, this);

    }


    public Integer countDevices() {
        return ((List<Device>) operationExecutor.execute(new GetOperationImpl(), this)).size();
    }

}
