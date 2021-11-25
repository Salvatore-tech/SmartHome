package com.gruppo1.smarthome.command.impl;

import com.gruppo1.smarthome.beans.ApplicationContextProvider;
import com.gruppo1.smarthome.command.api.CrudOperation;
import com.gruppo1.smarthome.repository.BaseSmartHomeRepository;

public class DeleteOperationImpl implements CrudOperation {

    @Override
    public Integer execute(Object item, String name) {
        BaseSmartHomeRepository repository = ApplicationContextProvider.getRepository(item);
        assert repository != null;
        return repository.deleteByName(name);
    }
}
