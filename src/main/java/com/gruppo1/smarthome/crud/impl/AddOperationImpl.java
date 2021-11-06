package com.gruppo1.smarthome.crud.impl;

import com.gruppo1.smarthome.crud.api.CrudOperation;
import com.gruppo1.smarthome.crud.beans.ApplicationContextProvider;
import com.gruppo1.smarthome.model.SmartHomeItem;
import org.springframework.data.repository.CrudRepository;


public class AddOperationImpl implements CrudOperation {

    @Override
    public SmartHomeItem execute(Object item) {
//        DeviceRepo deviceRepo = ApplicationContextProvider.getApplicationContext().getBean(DeviceRepo.class);

        CrudRepository repository = ApplicationContextProvider.getRepository(item);
        assert repository != null;
        return (SmartHomeItem) repository.save(item);
    }

}
