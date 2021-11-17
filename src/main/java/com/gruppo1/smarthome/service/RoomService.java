package com.gruppo1.smarthome.service;

import com.gruppo1.smarthome.crud.api.CrudOperation;
import com.gruppo1.smarthome.crud.beans.CrudOperationExecutor;
import com.gruppo1.smarthome.crud.impl.*;
import com.gruppo1.smarthome.crud.memento.Memento;
import com.gruppo1.smarthome.crud.memento.MementoCareTaker;
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
        if (Objects.nonNull(this.findRoomByName(room.getName())))
            return null;
        CrudOperation operationToPerform = new AddOperationImpl();
        // result = room.generateMemento()
        mementoCareTaker.add(new Memento(operationToPerform, room, "Add a room"));
        return (Room) operationExecutor.execute(operationToPerform, room);
    }

    public List<Room> findAllRoom() {
        CrudOperation operationToPerform = new GetOperationImpl();
        mementoCareTaker.add(new Memento(operationToPerform, null, "Get all rooms"));
        return (List<Room>) operationExecutor.execute(operationToPerform, this);
    }

    public Room findRoomByName(String name) {
        CrudOperation operationToPerform = new GetByNameOperationImpl();
        Room result = (Room) operationExecutor.execute(operationToPerform, name, this);
        mementoCareTaker.add(new Memento(operationToPerform, result, "Find a room given a name"));
        return result;
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
            mementoCareTaker.add(new Memento(operationToPerform, oldRoom, "Update a room"));
            return (Room) operationExecutor.execute(operationToPerform, updatedRoom);
        }
        return null;
    }

    public Integer deleteRoom(String name) {
        CrudOperation getByName = new GetByNameOperationImpl();
        List<Device> devices = (List<Device>) operationExecutor.execute(new GetOperationImpl(), "Device");
        if (!Objects.nonNull(devices) || name.equals("Default"))
            return 0;
        devices.forEach(device -> device.setRoom((Room) operationExecutor.execute(getByName, "Default", this)));
        CrudOperation operationToPerform = new DeleteOperationImpl();
        mementoCareTaker.add(new Memento(operationToPerform, null, "Delete room"));
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
//        mementoCareTaker.add(new Memento(operationToPerform), new SmartHomeItemLight(roomName, null));
        if (Objects.nonNull(room)) {
            if (Objects.nonNull(room.getDevices()))
                devices = room.getDevices();
        }

        return devices;
    }

    private Device changeRoom(String deviceName, String roomName) {
        CrudOperation operationToPerform = new GetByNameOperationImpl();
        Room room = (Room) operationExecutor.execute(operationToPerform, roomName, this);
        Device device = (Device) operationExecutor.execute(operationToPerform, deviceName, "Device");
//        mementoCareTaker.add(new Memento(operationToPerform), new SmartHomeItemLight(deviceName, roomName));
        if(Objects.nonNull(room) && Objects.nonNull(device)){
            device.setRoom(room);
            return device;
        }
        return null;
    }

}