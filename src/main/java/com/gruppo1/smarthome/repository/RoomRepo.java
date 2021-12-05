package com.gruppo1.smarthome.repository;

import com.gruppo1.smarthome.model.Room;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepo extends BaseSmartHomeRepository<Room, String> {
}
