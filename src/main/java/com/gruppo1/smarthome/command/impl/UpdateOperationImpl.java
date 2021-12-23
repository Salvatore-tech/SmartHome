package com.gruppo1.smarthome.command.impl;

import com.gruppo1.smarthome.command.api.CrudOperation;
import com.gruppo1.smarthome.model.SmartHomeItem;
import com.gruppo1.smarthome.repository.BaseSmartHomeRepository;

import java.util.Objects;

public class UpdateOperationImpl extends CrudOperation {

    public UpdateOperationImpl(BaseSmartHomeRepository repository) {
        super(repository);
    }

    @Override
    public SmartHomeItem execute(SmartHomeItem homeItem) {
        return (Objects.nonNull(repository)) ?
                (SmartHomeItem) repository.save(homeItem) : null;
    }
}
