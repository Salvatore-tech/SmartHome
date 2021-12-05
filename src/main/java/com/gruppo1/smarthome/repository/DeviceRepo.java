package com.gruppo1.smarthome.repository;

import com.gruppo1.smarthome.model.Device;
import com.gruppo1.smarthome.model.SmartHomeItem;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRepo extends BaseSmartHomeRepository<Device, String> {

    @Query("select d from Device d where d.room.name = :roomName")
    List<SmartHomeItem> findDevicesByRoomName(@Param("roomName") String roomName);
}
