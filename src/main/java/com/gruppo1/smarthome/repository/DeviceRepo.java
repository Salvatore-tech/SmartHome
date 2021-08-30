package com.gruppo1.smarthome.repository;

import com.gruppo1.smarthome.model.Device;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface DeviceRepo extends CrudRepository<Device, String> {
    void deleteDeviceById(String id);
    Optional<Device> findByName(String deviceName);
}
