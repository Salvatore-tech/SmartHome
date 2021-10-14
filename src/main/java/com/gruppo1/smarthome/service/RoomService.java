package com.gruppo1.smarthome.service;

import com.gruppo1.smarthome.model.Device;
import com.gruppo1.smarthome.model.Room;
import com.gruppo1.smarthome.repository.RoomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
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

    public Iterable<Room> findAllRoom() {
        return roomRepo.findAll();
    }

    public Room addRoom(Room room) {
        if (roomRepo.findByName(room.getName()).isPresent())
            return null;
        room.setId(UUID.randomUUID().toString());
        return roomRepo.save(room);
    }

    public Optional<Room> findRoomByName(String name) {
        return roomRepo.findByName(name);
    }

    public Boolean deleteRoom(String name) {
        List<Device> devices = findDevices(name);
        if (!Objects.nonNull(devices) || name.equals("Default"))
            return false;
        for (Device device : devices) {
            device.setRoom(roomRepo.findByName("Default").get());
        }
        roomRepo.deleteRoomByName(name);
        return true;
    }

    public Room updateRoom(String name, Room newRoom) {
        Optional<Room> oldRoom = roomRepo.findByName(name);
        if (!oldRoom.isPresent() || name.equals("Default") || roomRepo.findByName(newRoom.getName()).isPresent()) // TODO: move this check away
            return null;
        oldRoom.get().setName(newRoom.getName());
        return oldRoom.get();
    }

    public Device addDevice(String nameDevice, String nameRoom) {
        return changeRoom(nameDevice, nameRoom);
    }

    public Device deleteDevice(String nameDevice) {
        return changeRoom(nameDevice, "Default");
    }

    public List<Device> findDevices(String nameRoom) {
        Optional<Room> room = roomRepo.findByName(nameRoom);
        return room.isPresent() ? roomRepo.findAllDevices(nameRoom) : null;
    }

    private Device changeRoom(String deviceName, String roomName) {
        Optional<Room> room = roomRepo.findByName(roomName);
        Optional<Device> device = roomRepo.findDeviceByName(deviceName);
        if (room.isPresent() && device.isPresent()) {
            device.get().setRoom(room.get());
            return device.get();
        }
        return null;
    }

}