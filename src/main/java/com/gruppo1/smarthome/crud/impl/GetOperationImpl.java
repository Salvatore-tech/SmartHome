package com.gruppo1.smarthome.crud.impl;

import com.gruppo1.smarthome.crud.api.CrudOperation;
import com.gruppo1.smarthome.crud.beans.ApplicationContextProvider;
import com.gruppo1.smarthome.model.SmartHomeItem;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public class GetOperationImpl implements CrudOperation {
    @Override
    public List<SmartHomeItem> execute(Object item) {
        BaseSmartHomeRepository repository = ApplicationContextProvider.getRepository(item);
        assert repository != null;
        return (List<SmartHomeItem>) repository.findAll();
    }
}
