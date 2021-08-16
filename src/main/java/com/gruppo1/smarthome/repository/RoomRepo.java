package com.gruppo1.smarthome.repository;
import com.gruppo1.smarthome.model.Room;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoomRepo extends CrudRepository<Room, String> {

    void deleteRoomById(String id);
}
