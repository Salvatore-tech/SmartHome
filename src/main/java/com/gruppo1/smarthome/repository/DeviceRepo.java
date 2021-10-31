package com.gruppo1.smarthome.repository;

import com.gruppo1.smarthome.model.Device;
import com.gruppo1.smarthome.model.Room;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface DeviceRepo extends CrudRepository<Device, String> {

    void deleteDeviceByName(String name);

    Optional<Device> findByName(String deviceName);
}
