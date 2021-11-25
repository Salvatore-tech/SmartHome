package com.gruppo1.smarthome.command.impl;

import com.gruppo1.smarthome.beans.ApplicationContextProvider;
import com.gruppo1.smarthome.command.api.CrudOperation;
import com.gruppo1.smarthome.model.SmartHomeItem;
import com.gruppo1.smarthome.repository.BaseSmartHomeRepository;

public class GetByNameOperationImpl implements CrudOperation {

    @Override
    public SmartHomeItem execute(Object item, String name) {
        BaseSmartHomeRepository repository = ApplicationContextProvider.getRepository(item);
        assert repository != null;
        return repository.findByName(name).isPresent() ? (SmartHomeItem) repository.findByName(name).get() : null;
    }
}
