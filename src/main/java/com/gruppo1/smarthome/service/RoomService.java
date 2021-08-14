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

    public Room addRoom(Room room) {
        return roomRepo.save(room);
    }

    public List<Room> findAllRoom() {
        return (List<Room>) roomRepo.findAll();
    }

    public Room updateRoom(Room room) {
        return roomRepo.save(room);
    }

    public Optional<Room> findRoomByID(Long id){
        return roomRepo.findById(id);
    }
}