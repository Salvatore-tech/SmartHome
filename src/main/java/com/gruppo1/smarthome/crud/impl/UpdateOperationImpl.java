package com.gruppo1.smarthome.crud.impl;

import com.gruppo1.smarthome.crud.api.CrudOperation;
import com.gruppo1.smarthome.crud.beans.ApplicationContextProvider;
import com.gruppo1.smarthome.model.SmartHomeItem;

public class UpdateOperationImpl implements CrudOperation {
    @Override
    public SmartHomeItem execute(Object item) {
        BaseSmartHomeRepository repository = ApplicationContextProvider.getRepository(item);
        assert repository != null;
        return (SmartHomeItem) repository.save(item);
    }
}
