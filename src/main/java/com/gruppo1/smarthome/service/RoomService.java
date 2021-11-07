package com.gruppo1.smarthome.service;

import com.gruppo1.smarthome.crud.beans.CrudOperationExecutor;
import com.gruppo1.smarthome.crud.impl.*;
import com.gruppo1.smarthome.model.Device;
import com.gruppo1.smarthome.model.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Service
@Transactional
public class RoomService {
    private CrudOperationExecutor operationExecutor;

    @Autowired
    public RoomService(CrudOperationExecutor operationExecutor) {
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

    public Device addDevice(String nameDevice, String nameRoom) {
        return changeRoom(nameDevice, nameRoom);
    }

    public Device deleteDevice(String nameDevice) {
        return changeRoom(nameDevice, "Default");
    }

    public List<Device> findDevicesInRoom(String roomName) {
        List<Device> devices = new ArrayList<>();
        Room room = (Room) operationExecutor.execute(new GetByNameOperationImpl(), roomName, this);
        if(Objects.nonNull(room)) {
            if(Objects.nonNull(room.getDevices()))
                devices = room.getDevices();
        }

        return devices;
    }

    private Device changeRoom(String deviceName, String roomName) {
        Room room = (Room) operationExecutor.execute(new GetByNameOperationImpl(), roomName, this);
        Device device = (Device) operationExecutor.execute(new GetDeviceInRoomByNameImpl(), deviceName, this);
        if(Objects.nonNull(room) && Objects.nonNull(device)){
            device.setRoom(room);
            return device;
        }
        return null;
    }

}