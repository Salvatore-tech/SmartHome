package com.gruppo1.smarthome.crud.impl;

import com.gruppo1.smarthome.crud.api.CrudOperation;
import com.gruppo1.smarthome.crud.beans.ApplicationContextProvider;
import com.gruppo1.smarthome.model.SmartHomeItem;
import com.gruppo1.smarthome.repository.RoomRepo;

public class GetDeviceInRoomByNameImpl implements CrudOperation {
    @Override
    public SmartHomeItem execute(Object item, String name) {
        RoomRepo repository = (RoomRepo) ApplicationContextProvider.getRepository(item);
        assert repository != null;
        return repository.findDeviceByName(name);
    }
}
