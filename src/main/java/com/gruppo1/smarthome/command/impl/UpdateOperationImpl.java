package com.gruppo1.smarthome.command.impl;

import com.gruppo1.smarthome.beans.ApplicationContextProvider;
import com.gruppo1.smarthome.command.api.CrudOperation;
import com.gruppo1.smarthome.model.SmartHomeItem;
import com.gruppo1.smarthome.repository.BaseSmartHomeRepository;

public class UpdateOperationImpl implements CrudOperation {
    @Override
    public SmartHomeItem execute(Object item) {
        BaseSmartHomeRepository repository = ApplicationContextProvider.getRepository(item);
        assert repository != null;
        return (SmartHomeItem) repository.save(item);
    }
}
