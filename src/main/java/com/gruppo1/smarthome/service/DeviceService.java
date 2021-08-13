package com.gruppo1.smarthome.service;

import com.gruppo1.smarthome.model.Device;
import com.gruppo1.smarthome.repository.DeviceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class DeviceService {
    private final DeviceRepo deviceRepo;

    @Autowired
    public DeviceService(DeviceRepo deviceRepo) {
        this.deviceRepo = deviceRepo;
    }

    public Device addDevice(Device device) {
        device.setDeviceCode(UUID.randomUUID().toString());
        return deviceRepo.save(device);
    }

    public List<Device> findAllDevices(){
        return (List<Device>) deviceRepo.findAll();
    }

    public Optional<Device> findDeviceByID(Long id){
        return deviceRepo.findById(id);
    }

    public Device updateDevice(Device device){
        return deviceRepo.save(device);
    }

    public List<Device> deleteDevice(Long id){
        return null;
    }

    public long countDevices(){
        return deviceRepo.count();
    }


}
