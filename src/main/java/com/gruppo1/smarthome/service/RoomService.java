package com.gruppo1.smarthome.service;

import com.gruppo1.smarthome.crud.api.CrudOperation;
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
    private MementoCareTaker mementoCareTaker;

    @Autowired
    public RoomService(CrudOperationExecutor operationExecutor, MementoCareTaker mementoCareTaker) {
        this.operationExecutor = operationExecutor;
        this.mementoCareTaker = mementoCareTaker;
    }

    public Room addRoom(Room room) {
        //TODO SS: check if already exists
        CrudOperation operationToPerform = new AddOperationImpl();
        mementoCareTaker.add(operationToPerform.generateMemento(), new SmartHomeItemLight(room.getName(), "Room"));
        return (Room) operationExecutor.execute(operationToPerform, room);
    }

    public List<Room> findAllRoom() {
        CrudOperation operationToPerform = new GetOperationImpl();
        mementoCareTaker.add(operationToPerform.generateMemento(), new SmartHomeItemLight(null, "Room"));
        return (List<Room>) operationExecutor.execute(operationToPerform, this);
    }

    public Room findRoomByName(String name) {
        CrudOperation operationToPerform = new GetByNameOperationImpl();
        mementoCareTaker.add(operationToPerform.generateMemento(), new SmartHomeItemLight(name, "Room"));
        return (Room) operationExecutor.execute(operationToPerform, name, this);
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
            CrudOperation operationToPerform = new UpdateOperationImpl();
            mementoCareTaker.add(operationToPerform.generateMemento(), new SmartHomeItemLight(oldRoom.getName(), "Room"));
            return (Room) operationExecutor.execute(operationToPerform, updatedRoom);
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
        CrudOperation operationToPerform = new DeleteOperationImpl();
        mementoCareTaker.add(operationToPerform.generateMemento(), new SmartHomeItemLight(name, "Room")); //TODO
        return (Integer) operationExecutor.execute(operationToPerform, name, this);
    }


    // TODO SS: not yet implemented: Command and Memento

    public Device addDevice(String nameDevice, String nameRoom) {
        return changeRoom(nameDevice, nameRoom);
    }

    public Device deleteDevice(String nameDevice) {
        return changeRoom(nameDevice, "Default");
    }

    public List<Device> findDevicesInRoom(String roomName) {
        List<Device> devices = new ArrayList<>();
        CrudOperation operationToPerform = new GetByNameOperationImpl();
        Room room = (Room) operationExecutor.execute(operationToPerform, roomName, this);
        mementoCareTaker.add(operationToPerform.generateMemento(), new SmartHomeItemLight(roomName, null));
        if(Objects.nonNull(room)) {
            if(Objects.nonNull(room.getDevices()))
                devices = room.getDevices();
        }

        return devices;
    }

    private Device changeRoom(String deviceName, String roomName) {
        Room room = (Room) operationExecutor.execute(new GetByNameOperationImpl(), roomName, this);
        CrudOperation operationToPerform = new GetDeviceInRoomByNameImpl();
        Device device = (Device) operationExecutor.execute(operationToPerform, deviceName, this);
        mementoCareTaker.add(operationToPerform.generateMemento(), new SmartHomeItemLight(deviceName, roomName));
        if(Objects.nonNull(room) && Objects.nonNull(device)){
            device.setRoom(room);
            return device;
        }
        return null;
    }

}