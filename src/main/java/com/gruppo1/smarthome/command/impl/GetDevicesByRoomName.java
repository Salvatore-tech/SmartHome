package com.gruppo1.smarthome.command.impl;

import com.gruppo1.smarthome.command.api.CrudOperation;
import com.gruppo1.smarthome.model.SmartHomeItem;
import com.gruppo1.smarthome.repository.BaseSmartHomeRepository;
import com.gruppo1.smarthome.repository.DeviceRepo;

import java.util.List;
import java.util.Objects;

public class GetDevicesByRoomName implements CrudOperation {

    private DeviceRepo repository;

    public GetDevicesByRoomName(DeviceRepo repository) {
        this.repository = repository;
    }

    @Override
    public List<SmartHomeItem> execute(String roomName) {
        if (Objects.isNull(repository))
            return null;
        return repository.findDevicesByRoomName(roomName);
    }

    @Override
    public BaseSmartHomeRepository getRepository() {
        return repository;
    }
}
