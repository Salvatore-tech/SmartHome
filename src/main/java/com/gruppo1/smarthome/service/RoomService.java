package com.gruppo1.smarthome.service;

import com.gruppo1.smarthome.beans.CrudOperationExecutor;
import com.gruppo1.smarthome.command.api.CrudOperation;
import com.gruppo1.smarthome.command.impl.*;
import com.gruppo1.smarthome.memento.Memento;
import com.gruppo1.smarthome.memento.MementoCareTaker;
import com.gruppo1.smarthome.model.Device;
import com.gruppo1.smarthome.model.Room;
import com.gruppo1.smarthome.model.SmartHomeItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class RoomService {
    private final CrudOperationExecutor operationExecutor;
    private final MementoCareTaker mementoCareTaker;

    @Autowired
    public RoomService(CrudOperationExecutor operationExecutor, MementoCareTaker mementoCareTaker) {
        this.operationExecutor = operationExecutor;
        this.mementoCareTaker = mementoCareTaker;
    }

    public Room addRoom(Room room) {
        CrudOperation operationToPerform = new AddOperationImpl();
        if (!isPresent((Room) operationExecutor.execute(new GetByNameOperationImpl(), room.getName(), this))) {
            mementoCareTaker.add(new Memento(operationToPerform, room, "Add a room"));
            return (Room) operationExecutor.execute(operationToPerform, room);
        }
        return null;
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
        CrudOperation operationToPerform = new UpdateOperationImpl();
        if (!isUpdatable(roomNameToUpdate)) {
            return null;
        }
        Room oldRoom = (Room) operationExecutor.execute(new GetByNameOperationImpl(), roomNameToUpdate, this);
        if (!validateUpdate(oldRoom, updatedRoom))
            return null;
        oldRoom.setName(updatedRoom.getName());
        mementoCareTaker.add(new Memento(operationToPerform, oldRoom, "Update a room"));
        return (Room) operationExecutor.execute(operationToPerform, oldRoom);
    }

    public Integer deleteRoom(String roomName) {
        CrudOperation operationToPerform = new DeleteOperationImpl();
        if (!isUpdatable(roomName))
            return null;

        List<Device> devices = (List<Device>) operationExecutor.execute(new GetDevicesByRoomName(), roomName, "Device");
        if (Objects.nonNull(devices)) {
            Room defaultRoom = (Room) operationExecutor.execute(new GetByNameOperationImpl(), "Default", this);
            devices.forEach(device -> device.setRoom(defaultRoom));
        }
        mementoCareTaker.add(new Memento(operationToPerform, null, "Delete room"));
        return (Integer) operationExecutor.execute(operationToPerform, roomName, this);
    }


    public Device addDeviceToRoom(String deviceName, String roomName) {
        CrudOperation getOperation = new GetByNameOperationImpl();
        Device device = (Device) operationExecutor.execute(getOperation, deviceName, "Device");
        Room room = (Room) operationExecutor.execute(getOperation, roomName, this);
        if (validateDeviceAndRoom(device, room)) {
            device.setRoom(room);
        }
        return device;
    }

    public Device deleteDeviceFromRoom(String roomName, String deviceName) {
        CrudOperation operationToPerform = new GetByNameOperationImpl();
        Device device = (Device) operationExecutor.execute(operationToPerform, deviceName, "Device");
        Room room = (Room) operationExecutor.execute(operationToPerform, roomName, this);
        if (validateDeviceAndRoom(device, room)) {
            Room defaultRoom = (Room) operationExecutor.execute(new GetByNameOperationImpl(), "Default", this);
            device.setRoom(defaultRoom);
        }
        return device;
    }

    public List<Device> findDevicesInRoom(String roomName) {
        List<Device> devices = new ArrayList<>();
        Room room = (Room) operationExecutor.execute(new GetByNameOperationImpl(), roomName, this);
        if (isPresent(room)) {
            CrudOperation operationToPerform = new GetDevicesByRoomName();
            mementoCareTaker.add(new Memento(operationToPerform, null, "Find devices in room"));
            devices = (List<Device>) operationExecutor.execute(operationToPerform, roomName, "Device");
        }
        return devices;
    }

    private boolean isUpdatable(String roomName) {
        return !roomName.equals("Default");
    }

    private boolean validateDeviceAndRoom(Device device, Room room) {
        return isPresent(device) && isPresent(room);
    }

    private Boolean isPresent(SmartHomeItem item) {
        return Objects.nonNull(item);
    }

    private Boolean validateUpdate(Room oldRoom, Room updatedRoom) {
        if (!isPresent(oldRoom)) // No room to update
            return false;
        Room persistentRoom = (Room) operationExecutor.execute(new GetByNameOperationImpl(), updatedRoom.getName(), this);
        return !isPresent(persistentRoom) || persistentRoom.getName().equalsIgnoreCase(updatedRoom.getName()); // Check if the new name violates unique constraint
    }

}