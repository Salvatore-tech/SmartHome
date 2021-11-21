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
    private final CrudOperationExecutor operationExecutor;
    private final MementoCareTaker mementoCareTaker;
    private CrudOperation operationToPerform;

    @Autowired
    public RoomService(CrudOperationExecutor operationExecutor, MementoCareTaker mementoCareTaker) {
        this.operationExecutor = operationExecutor;
        this.mementoCareTaker = mementoCareTaker;
    }

    //TODO FIX MEMENTO IN ALL METHODS

    public Room addRoom(Room room) {
        if (validateRoom(room)){
            operationToPerform = new AddOperationImpl();
            mementoCareTaker.add(new Memento(operationToPerform, room, "Add a room"));
            return (Room) operationExecutor.execute(operationToPerform, room);
        }
        return null;
    }

    public List<Room> findAllRoom() {
        operationToPerform = new GetOperationImpl();
        mementoCareTaker.add(new Memento(operationToPerform, null, "Get all rooms"));
        return (List<Room>) operationExecutor.execute(operationToPerform, this);
    }

    public Room findRoomByName(String name) {
        operationToPerform = new GetByNameOperationImpl();
        Room result = (Room) operationExecutor.execute(operationToPerform, name, this);
        mementoCareTaker.add(new Memento(operationToPerform, result, "Find a room given a name"));
        return result;
    }

    public Room updateRoom(String roomNameToUpdate, Room updatedRoom) {
        if(!roomNameToUpdate.equals("Default")){
            operationToPerform = new GetByNameOperationImpl();
            Room oldRoom = (Room) operationExecutor.execute(operationToPerform, roomNameToUpdate, this);
            if (validateUpdate(oldRoom, updatedRoom)){
                oldRoom.setName(updatedRoom.getName());
                operationToPerform = new UpdateOperationImpl();
                mementoCareTaker.add(new Memento(operationToPerform, oldRoom, "Update a room"));
                return (Room) operationExecutor.execute(operationToPerform, oldRoom);
            }
        }
        return null;
    }

    public Integer deleteRoom(String roomName) {
        if (roomName.equals("Default"))
            return 0;
       operationToPerform = new GetDevicesByRoomName();
        List<Device> devices = (List<Device>) operationExecutor.execute(operationToPerform, roomName,"Device" );
        if(Objects.nonNull(devices)){
            operationToPerform = new GetByNameOperationImpl();
            Room defaultRoom = (Room) operationExecutor.execute(operationToPerform, "Default", this);
            devices.forEach(device -> device.setRoom(defaultRoom));
        }
        operationToPerform = new DeleteOperationImpl();
        mementoCareTaker.add(new Memento(operationToPerform, null, "Delete room"));
        return (Integer) operationExecutor.execute(operationToPerform, roomName, this);
    }

    public Device addDevice(String nameDevice, String nameRoom) {
        return changeRoom(nameDevice, nameRoom);
    }

    public Device deleteDevice(String nameDevice) {
        return changeRoom(nameDevice, "Default");
    }

    public List<Device> findDevicesInRoom(String roomName) {
        List<Device> devices = new ArrayList<>();
        operationToPerform = new GetByNameOperationImpl();
        Room room = (Room) operationExecutor.execute(operationToPerform, roomName, this);
        if (Objects.nonNull(room)) {
            operationToPerform = new GetDevicesByRoomName();
            devices = (List<Device>) operationExecutor.execute(operationToPerform, roomName,"Device" );
        }
        return devices;
    }

    private Device changeRoom(String deviceName, String roomName) {
        operationToPerform = new GetByNameOperationImpl();
        Room room = (Room) operationExecutor.execute(operationToPerform, roomName, this);
        Device device = (Device) operationExecutor.execute(operationToPerform, deviceName, "Device");
        if(Objects.nonNull(room) && Objects.nonNull(device)){
            device.setRoom(room);
            return device;
        }
        return null;
    }

    public Integer countRooms() {
        operationToPerform = new GetOperationImpl();
        mementoCareTaker.add(new Memento(operationToPerform, new Room("Room"), "Count rooms")); //TODO
        return ((List<Room>) operationExecutor.execute(operationToPerform, "Room")).size();
    }

    private Boolean validateRoom(Room room){
        operationToPerform = new GetByNameOperationImpl();
        return Objects.isNull(operationExecutor.execute(operationToPerform, room.getName(), this));
    }

    private Boolean validateUpdate(Room oldRoom, Room updatedRoom){
        if(Objects.nonNull(oldRoom)){
            operationToPerform = new GetByNameOperationImpl();
            Room roomToCheck = (Room) operationExecutor.execute(operationToPerform, updatedRoom.getName(), this);
            if(Objects.isNull(roomToCheck)){
                return true;
            }
        }
        return false;
    }

}