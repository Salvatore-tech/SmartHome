package com.gruppo1.smarthome.command.impl;

import com.gruppo1.smarthome.beans.ApplicationContextProvider;
import com.gruppo1.smarthome.command.api.CrudOperation;
import com.gruppo1.smarthome.model.Device;
import com.gruppo1.smarthome.repository.DeviceRepo;

import java.util.List;
import java.util.Objects;

public class GetDevicesByRoomName implements CrudOperation {

    @Override
    public List<Device> execute(Object item, String roomName) {
        DeviceRepo repository = (DeviceRepo) ApplicationContextProvider.getRepository(item);
        assert repository != null;
        List<Device> devicesInTheRoom = repository.findDevicesByRoomName(roomName);
        return Objects.nonNull(devicesInTheRoom) ?
                devicesInTheRoom : null;
    }
}
