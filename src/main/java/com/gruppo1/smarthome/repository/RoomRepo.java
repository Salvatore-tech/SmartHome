package com.gruppo1.smarthome.repository;

import com.gruppo1.smarthome.crud.api.GenericRepository;
import com.gruppo1.smarthome.model.Device;
import com.gruppo1.smarthome.model.Room;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoomRepo extends GenericRepository<Room, String> {

    @Query("select d from Device d where d.name = :deviceName")
    Device findDeviceByName(@Param("deviceName") String deviceName);

}
