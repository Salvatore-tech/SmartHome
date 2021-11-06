package com.gruppo1.smarthome.service;

import com.gruppo1.smarthome.crud.beans.CrudOperationExecutor;
import com.gruppo1.smarthome.crud.impl.*;
import com.gruppo1.smarthome.model.Device;
import com.gruppo1.smarthome.model.Room;
import com.gruppo1.smarthome.repository.RoomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class RoomService {
    private CrudOperationExecutor operationExecutor;
    private final RoomRepo roomRepo;

    @Autowired
    public RoomService(RoomRepo roomRepo, CrudOperationExecutor operationExecutor) {
        this.roomRepo = roomRepo;
        this.operationExecutor = operationExecutor;
    }

    public Room addRoom(Room room) {
        //TODO SS: check if already exists
        return (Room) operationExecutor.execute(new AddOperationImpl(), room);
    }

    public List<Room> findAllRoom() {
        return (List<Room>) operationExecutor.execute(new GetOperationImpl(), this);
    }

    public Room findRoomByName(String name) {
        return (Room) operationExecutor.execute(new GetByNameOperationImpl(), name, this);
    }

    public Room updateRoom(String roomNameToUpdate, Room updatedRoom) {
//        Optional<Room> oldRoom = roomRepo.findByName(name);
//        if (!oldRoom.isPresent() || name.equals("Default") || roomRepo.findByName(newRoom.getName()).isPresent()) // TODO
//            return null;
//        oldRoom.get().setName(newRoom.getName());
//        return oldRoom.get();

        //TODO SS: hide more the id handling

        Room oldRoom = (Room) operationExecutor.execute(new GetByNameOperationImpl(), roomNameToUpdate, this);
        if (Objects.nonNull(oldRoom)) {
            updatedRoom.setId(oldRoom.getId());
            return (Room) operationExecutor.execute(new UpdateOperationImpl(), updatedRoom);
        }
        return null;
    }

    public Integer deleteRoom(String name) {
//        List<Device> devices = findDevices(name);
//        if (!Objects.nonNull(devices) || name.equals("Default"))
//            return false;
//        for (Device device : devices) {
//            device.setRoom(roomRepo.findByName("Default").get());
//        }
//        roomRepo.deleteRoomByName(name);
//        return true;
        //TODO check if already exists
        return (Integer) operationExecutor.execute(new DeleteOperationImpl(), name, this);
    }


    // TODO SS: not yet implemented

    public Device addDevice(String nameDevice, String nameRoom) {
        return changeRoom(nameDevice, nameRoom);
    }

    public Device deleteDevice(String nameDevice) {
        return changeRoom(nameDevice, "Default");
    }

    public List<Device> findDevices(String nameRoom) {
        Optional<Room> room = roomRepo.findByName(nameRoom);
//        return room.isPresent() ? roomRepo.findAllDevices(nameRoom) : null;
        return null;
    }

    private Device changeRoom(String deviceName, String roomName) {
        Optional<Room> room = roomRepo.findByName(roomName);
//        Optional<Device> device = roomRepo.findDeviceByName(deviceName);
//        if (room.isPresent() && device.isPresent()) {
//            device.get().setRoom(room.get());
//            return device.get();
//        }
        return null;
    }

}