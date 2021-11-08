package com.gruppo1.smarthome.crud.impl;

import com.gruppo1.smarthome.crud.api.BaseSmartHomeRepository;
import com.gruppo1.smarthome.crud.api.CrudOperation;
import com.gruppo1.smarthome.crud.beans.ApplicationContextProvider;
import com.gruppo1.smarthome.model.SmartHomeItem;

public class GetByNameOperationImpl implements CrudOperation {
    @Override
    public SmartHomeItem execute(Object item, String name) {

        BaseSmartHomeRepository repository = ApplicationContextProvider.getRepository(item);
        assert repository != null;
        return repository.findByName(name).isPresent() ? (SmartHomeItem) repository.findByName(name).get() : null;

    }
}
