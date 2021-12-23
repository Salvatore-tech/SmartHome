package com.gruppo1.smarthome.command.impl;

import com.gruppo1.smarthome.command.api.CrudOperation;
import com.gruppo1.smarthome.model.SmartHomeItem;
import com.gruppo1.smarthome.repository.BaseSmartHomeRepository;

import java.util.Objects;

public class DeleteOperationImpl extends CrudOperation {

    public DeleteOperationImpl(BaseSmartHomeRepository repository) {
        super(repository);
    }

    @Override
    public Integer execute(String homeItemName) {
        if (Objects.nonNull(repository))
            return repository.deleteByName(homeItemName);
        return 0;
    }

    @Override
    public Integer execute(SmartHomeItem homeItem) {
        if (Objects.nonNull(repository))
            return repository.deleteByName(homeItem.getName());
        return 0;
    }
}
