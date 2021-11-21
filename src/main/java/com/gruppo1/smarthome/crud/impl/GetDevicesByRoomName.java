package com.gruppo1.smarthome.crud.impl;

import com.gruppo1.smarthome.crud.api.CrudOperation;
import com.gruppo1.smarthome.crud.beans.ApplicationContextProvider;
import com.gruppo1.smarthome.model.Device;
import com.gruppo1.smarthome.repository.DeviceRepo;
import java.util.List;
import java.util.Objects;

public class GetDevicesByRoomName implements CrudOperation{
    @Override
    public List<Device> execute(Object item, String roomName) {
        DeviceRepo repository = (DeviceRepo) ApplicationContextProvider.getRepository(item);
        assert repository != null;
        return Objects.nonNull(repository.finDevicesByRoomName(roomName)) ?
                repository.finDevicesByRoomName(roomName) : null;
    }
}
