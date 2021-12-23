package com.gruppo1.smarthome.service;

import com.gruppo1.smarthome.command.api.CrudOperation;
import com.gruppo1.smarthome.command.impl.*;
import com.gruppo1.smarthome.model.Device;
import com.gruppo1.smarthome.model.MementoCareTaker;
import com.gruppo1.smarthome.model.Room;
import com.gruppo1.smarthome.model.SmartHomeItem;
import com.gruppo1.smarthome.repository.DeviceRepo;
import com.gruppo1.smarthome.repository.RoomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class RoomService {
    private final MementoCareTaker mementoCareTaker;
    private final RoomRepo roomRepo;
    private final DeviceRepo deviceRepo;

    @Autowired
    public RoomService(MementoCareTaker mementoCareTaker, RoomRepo roomRepo, DeviceRepo deviceRepo) {
        this.mementoCareTaker = mementoCareTaker;
        this.roomRepo = roomRepo;
        this.deviceRepo = deviceRepo;
    }

    public Room addRoom(Room room) {
        CrudOperation addOperation = new AddOperationImpl(roomRepo);
        CrudOperation getOperation = new GetByNameOperationImpl(roomRepo);
        if (!isPresent(getOperation.execute(room.getName()))) {
            mementoCareTaker.push(addOperation, room.createMemento());
            return addOperation.execute(room);
        }
        return null;
    }

    public List<Room> findAllRoom() {
        CrudOperation operationToPerform = new GetOperationImpl(roomRepo);
        mementoCareTaker.push(operationToPerform, null);
        return (List<Room>) (List<?>) operationToPerform.execute();
    }

    public Room findRoomByName(String name) {
        CrudOperation operationToPerform = new GetByNameOperationImpl(roomRepo);
        mementoCareTaker.push(operationToPerform, null); // TODO SS
        return operationToPerform.execute(name);
    }

    public Room updateRoom(String roomNameToUpdate, Room updatedRoom) {
        CrudOperation operationToPerform = new UpdateOperationImpl(roomRepo);
        if (!isUpdatable(roomNameToUpdate)) {
            return null;
        }
        CrudOperation getByNameOperation = new GetByNameOperationImpl(roomRepo);
        Room oldRoom = getByNameOperation.execute(roomNameToUpdate);
        if (!validateUpdate(oldRoom, updatedRoom))
            return null;
        mementoCareTaker.push(operationToPerform, oldRoom.createMemento());
        oldRoom.setName(updatedRoom.getName());
        return operationToPerform.execute(oldRoom);
    }

    public Integer deleteRoom(String roomName) {
        CrudOperation deleteOperation = new DeleteOperationImpl(roomRepo);
        CrudOperation getDevicesOperation = new GetDevicesByRoomName(deviceRepo);
        CrudOperation getRoomOperation = new GetByNameOperationImpl(roomRepo);
        if (!isUpdatable(roomName))
            return 0;

        Room roomToDelete = getRoomOperation.execute(roomName);
        if (Objects.isNull(roomToDelete))
            return 0;

        mementoCareTaker.push(deleteOperation, roomToDelete.createMemento());

        List<Device> devices = getDevicesOperation.execute(roomName);
        if (Objects.nonNull(devices)) {
            Room defaultRoom = getRoomOperation.execute("Default");
            devices.forEach(device -> device.setRoom(defaultRoom));
        }
        return deleteOperation.execute(roomToDelete);
    }


    public Device addDeviceToRoom(String deviceName, String roomName) {
        CrudOperation getDeviceOperation = new GetByNameOperationImpl(deviceRepo);
        CrudOperation getRoomOperation = new GetByNameOperationImpl(roomRepo);
        Device device = getDeviceOperation.execute(deviceName);
        Room room = getRoomOperation.execute(roomName);
        if (validateDeviceAndRoom(device, room)) {
            device.setRoom(room);
            return device;
        }
        return null;
    }

    // Bedroom -> A TODO
    // Se A non è in Bedroom => non lo elimina

    public Boolean deleteDeviceFromRoom(String roomName, String deviceName) {
        CrudOperation getDeviceOperation = new GetByNameOperationImpl(deviceRepo);
        CrudOperation getRoomOperation = new GetByNameOperationImpl(roomRepo);

        Room room = getRoomOperation.execute(roomName);
        Device device = getDeviceOperation.execute(deviceName);
        if (isPresent(room) && isPresent(device)) {
            Room defaultRoom = getRoomOperation.execute("Default");
            device.setRoom(defaultRoom);
            return true;
        }
        return false;
    }

    public List<Device> findDevicesInRoom(String roomName) {
        CrudOperation getRoomOperation = new GetByNameOperationImpl(roomRepo);
        CrudOperation getDevicesInRoom = new GetDevicesByRoomName(deviceRepo);
        List<Device> devices = new ArrayList<>();

        Room room = getRoomOperation.execute(roomName);
        if (isPresent(room)) {
            devices = getDevicesInRoom.execute(roomName);
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
        CrudOperation getRoomOperation = new GetByNameOperationImpl(roomRepo);
        Room persistentRoom = getRoomOperation.execute(updatedRoom.getName());
        return !isPresent(persistentRoom) || persistentRoom.getName().equalsIgnoreCase(updatedRoom.getName()); // Check if the new name violates unique constraint
    }

}