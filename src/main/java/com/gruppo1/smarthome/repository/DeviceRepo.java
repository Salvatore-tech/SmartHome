package com.gruppo1.smarthome.repository;

import com.gruppo1.smarthome.model.Device;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DeviceRepo extends BaseSmartHomeRepository<Device, String> {

    @Query("select d from Device d where d.room.name = :roomName")
    List<Device> findDevicesByRoomName(@Param("roomName") String roomName);
}
