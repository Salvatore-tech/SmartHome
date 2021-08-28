package com.gruppo1.smarthome.service;

import com.gruppo1.smarthome.model.Device;
import com.gruppo1.smarthome.model.Room;
import com.gruppo1.smarthome.repository.RoomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
        room.setId(UUID.randomUUID().toString());
        return roomRepo.save(room);
    }

    public Optional<Room> findRoomByName(String name) {
        return roomRepo.findByName(name);
    }

    public Boolean deleteRoom(String name) {
        //TODO
        roomRepo.deleteRoomByName(name);
        return true;
    }

    public Room updateRoom(String name, Room room) {
        Optional<Room> oldRoom = roomRepo.findByName(name);
        if(oldRoom.isPresent()){
            room.setId(oldRoom.get().getId());
        }
        return roomRepo.save(room);
    }

    public Optional<Device> addDevice(String nameDevice, String nameRoom){
        Optional<Device> device = changeRoom(nameDevice, nameRoom);
        return device.isPresent() ? device : null;
    }

    public Optional<Device> deleteDevice(String nameDevice){
        Optional<Device> device = changeRoom(nameDevice, "Default");
        return device.isPresent() ? device : null;
    }

    public Optional<List<Device>> findDevices(String nameRoom){
        Optional<Room> room = roomRepo.findByName(nameRoom);
        return room.isPresent() ? roomRepo.findAllDevices(nameRoom) : null;
    }

    private Optional<Device> changeRoom(String nameDevice, String nameRoom){
        Optional<Room> room = roomRepo.findByName(nameRoom);
        System.out.println(room);
        Optional<Device> device = roomRepo.findDeviceByName(nameDevice);
        System.out.println(device);
        if(room.isPresent() && device.isPresent()){
            device.get().setRoom(room.get());
            return device;
        }
        return null;
    }

}