package com.gruppo1.smarthome.service;

import com.gruppo1.smarthome.model.Device;
import com.gruppo1.smarthome.repository.DeviceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DeviceService {
    private final DeviceRepo deviceRepo;

    @Autowired
    public DeviceService(DeviceRepo deviceRepo) {
        this.deviceRepo = deviceRepo;
    }

    public Device addDevice(Device device) {
        if (deviceRepo.findByName(device.getName()).isPresent())
            return null;
//        device.setId(UUID.randomUUID().toString());
        return deviceRepo.save(device);
    }

    public List<Device> findAllDevices(){
        return (List<Device>) deviceRepo.findAll();
    }

    public Optional<Device> findDeviceByName(String name) {
        return deviceRepo.findByName(name);
    }

    public Device updateDevice(Device device){
        if (deviceRepo.findByName(device.getName()).isPresent()) {
            deviceRepo.save(device);
            return device;
        }
        return null;
    }

    public Boolean deleteDevice(String name){
        Optional<Device> device = deviceRepo.findByName(name);
        if(device.isPresent()){
            deviceRepo.deleteDeviceByName(name);
            return true;
        }
        return false;
    }


    public long countDevices(){
        return deviceRepo.count();
    }


}
