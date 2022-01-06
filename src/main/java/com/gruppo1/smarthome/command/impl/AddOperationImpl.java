package com.gruppo1.smarthome.command.impl;

import com.gruppo1.smarthome.command.api.CrudOperation;
import com.gruppo1.smarthome.model.SmartHomeItem;
import com.gruppo1.smarthome.repository.BaseSmartHomeRepository;

import java.util.Objects;


public class AddOperationImpl extends CrudOperation {

    public AddOperationImpl(BaseSmartHomeRepository repository) {
        super(repository);
    }

    @Override
    public SmartHomeItem execute(SmartHomeItem homeItem) {
        if (Objects.isNull(repository))
            return null;
        return (SmartHomeItem) repository.save(homeItem);
    }
}
