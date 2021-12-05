package com.gruppo1.smarthome.command.impl;

import com.gruppo1.smarthome.command.api.CrudOperation;
import com.gruppo1.smarthome.model.SmartHomeItem;
import com.gruppo1.smarthome.repository.BaseSmartHomeRepository;

import java.util.Collections;
import java.util.List;
import java.util.Objects;


public class AddOperationImpl implements CrudOperation {

    private final BaseSmartHomeRepository repository;

    public AddOperationImpl(BaseSmartHomeRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<SmartHomeItem> execute(SmartHomeItem homeItem) {
        if (Objects.isNull(repository))
            return null;

        return (Objects.nonNull(repository)) ?
                Collections.singletonList((SmartHomeItem) repository.save(homeItem)) : null;
    }

    @Override
    public BaseSmartHomeRepository getRepository() {
        return repository;
    }
}
