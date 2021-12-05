package com.gruppo1.smarthome.command.impl;

import com.gruppo1.smarthome.command.api.CrudOperation;
import com.gruppo1.smarthome.model.SmartHomeItem;
import com.gruppo1.smarthome.repository.BaseSmartHomeRepository;

import java.util.List;
import java.util.Objects;

public class GetOperationImpl implements CrudOperation {

    private BaseSmartHomeRepository repository;

    public GetOperationImpl(BaseSmartHomeRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<SmartHomeItem> execute() {
        return Objects.nonNull(repository) ? (List<SmartHomeItem>) repository.findAll() : null;
    }

    @Override
    public BaseSmartHomeRepository getRepository() {
        return repository;
    }
}
