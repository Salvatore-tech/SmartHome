package com.gruppo1.smarthome.command.impl;

import com.gruppo1.smarthome.command.api.CrudOperation;
import com.gruppo1.smarthome.model.SmartHomeItem;
import com.gruppo1.smarthome.repository.BaseSmartHomeRepository;

import java.util.Objects;

public class DeleteOperationImpl implements CrudOperation {

    private final BaseSmartHomeRepository repository;

    public DeleteOperationImpl(BaseSmartHomeRepository repository) {
        this.repository = repository;
    }

    @Override
    public int executeDelete(SmartHomeItem homeItem) {
        if (Objects.nonNull(repository))
            return repository.deleteByName(homeItem.getName());
        return 0;
    }

    @Override
    public BaseSmartHomeRepository getRepository() {
        return repository;
    }

    //    @Override
//    public Integer execute(Object item, String name) {
//        BaseSmartHomeRepository repository = ApplicationContextProvider.getRepository(item);
//        assert repository != null;
//        return repository.deleteByName(name);
//    }
}
