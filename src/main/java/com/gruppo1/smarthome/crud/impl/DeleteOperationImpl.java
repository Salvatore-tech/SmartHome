package com.gruppo1.smarthome.crud.impl;

import com.gruppo1.smarthome.crud.api.BaseSmartHomeRepository;
import com.gruppo1.smarthome.crud.api.CrudOperation;
import com.gruppo1.smarthome.crud.beans.ApplicationContextProvider;

public class DeleteOperationImpl implements CrudOperation {
    @Override
    public Integer execute(Object item, String name) {
        BaseSmartHomeRepository repository = ApplicationContextProvider.getRepository(item);
        assert repository != null;
        return repository.deleteByName(name);
    }
}
