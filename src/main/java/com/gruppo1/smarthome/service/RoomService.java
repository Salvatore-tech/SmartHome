package com.gruppo1.smarthome.service;

import com.gruppo1.smarthome.model.Room;
import com.gruppo1.smarthome.repository.RoomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RoomService {
    private final RoomRepo roomRepo;

    @Autowired
    public RoomService(RoomRepo roomRepo) {
        this.roomRepo = roomRepo;
    }

    public List<Room> findAllRoom() {
        return (List<Room>) roomRepo.findAll();
    }

    public Room addRoom(Room room) {
        return roomRepo.save(room);
    }

    public Optional<Room> findRoomByID(String id) {
        return roomRepo.findById(id);
    }

    public Boolean deleteRoom(String id) {
        if (roomRepo.findById(id).isPresent()) {
            roomRepo.deleteRoomById(id);
            return true;
        }
        return false;
    }

    public Room updateRoom(Room room) {
        return roomRepo.save(room);
    }

}