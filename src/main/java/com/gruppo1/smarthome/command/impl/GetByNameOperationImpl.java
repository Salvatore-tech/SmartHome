package com.gruppo1.smarthome.command.impl;

import com.gruppo1.smarthome.command.api.CrudOperation;
import com.gruppo1.smarthome.model.SmartHomeItem;
import com.gruppo1.smarthome.repository.BaseSmartHomeRepository;

import java.util.Objects;

public class GetByNameOperationImpl extends CrudOperation {

    public GetByNameOperationImpl(BaseSmartHomeRepository repository) {
        super(repository);
    }

    @Override
    public SmartHomeItem execute(String homeItemName) {
        if (Objects.isNull(repository))
            return null;
        return (repository.findByName(homeItemName).isPresent()) ? (SmartHomeItem) repository.findByName(homeItemName).get() : null;
    }
}


