package com.gruppo1.smarthome.command.impl;

import com.gruppo1.smarthome.command.api.CrudOperation;
import com.gruppo1.smarthome.model.SmartHomeItem;
import com.gruppo1.smarthome.repository.BaseSmartHomeRepository;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class GetByNameOperationImpl implements CrudOperation {

    private BaseSmartHomeRepository repository;

    public GetByNameOperationImpl(BaseSmartHomeRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<SmartHomeItem> execute(String homeItemName) {
        if (Objects.isNull(repository))
            return null;
        return repository.findByName(homeItemName).isPresent() ? Collections.singletonList((SmartHomeItem) repository.findByName(homeItemName).get()) : null;
    }

    @Override
    public BaseSmartHomeRepository getRepository() {
        return repository;
    }
}

//    @Override
//    public SmartHomeItem execute(Object item, String name) {
//        BaseSmartHomeRepository repository = ApplicationContextProvider.getRepository(item);
//        assert repository != null;
//        return repository.findByName(name).isPresent() ? (SmartHomeItem) repository.findByName(name).get() : null;
//    }

