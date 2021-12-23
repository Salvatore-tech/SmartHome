package com.gruppo1.smarthome.command.impl;

import com.gruppo1.smarthome.command.api.CrudOperation;
import com.gruppo1.smarthome.model.Device;
import com.gruppo1.smarthome.repository.DeviceRepo;

import java.util.List;
import java.util.Objects;

public class GetDevicesByRoomName extends CrudOperation {

    public GetDevicesByRoomName(DeviceRepo repository) {
        super(repository);
    }

    @Override
    public List<Device> execute(String roomName) {
        if (Objects.isNull(repository))
            return null;
        return ((DeviceRepo) repository).findDevicesByRoomName(roomName);
    }
}
