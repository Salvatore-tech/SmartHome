package com.gruppo1.smarthome.repository;
import com.gruppo1.smarthome.model.Device;
import com.gruppo1.smarthome.model.Room;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface RoomRepo extends CrudRepository<Room, String> {

    void deleteRoomByName(String name);

    Optional<Room> findByName(String name);

    /* OLD QUERY

    @Query("select d from Device d where d.room.name = :name")
    List<Device> findAllDevices(@Param("name") String name);

     */

    @Query("select d from Device d where d.name = :nameDevice")
    Optional<Device> findDeviceByName(@Param("nameDevice") String nameDevice);

    @Query("select r.devices from Room r where r.name = :name")
    List<Device> findAllDevices(@Param("name") String name);

}
