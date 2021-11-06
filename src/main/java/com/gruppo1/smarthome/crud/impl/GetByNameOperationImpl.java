package com.gruppo1.smarthome.crud.impl;

import com.gruppo1.smarthome.crud.api.CrudOperation;
import com.gruppo1.smarthome.crud.api.GenericRepository;
import com.gruppo1.smarthome.crud.beans.ApplicationContextProvider;
import com.gruppo1.smarthome.model.SmartHomeItem;

public class GetByNameOperationImpl implements CrudOperation {
    @Override
    public SmartHomeItem execute(Object item, String name) {
//        CrudRepository repository = ApplicationContextProvider.getRepository(item);
        GenericRepository repository = (GenericRepository) ApplicationContextProvider.getRepository(item);
        assert repository != null;
        return (SmartHomeItem) repository.findByName(name).get();
    }
}
