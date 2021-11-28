package com.gruppo1.smarthome.command.impl;

import com.gruppo1.smarthome.beans.ApplicationContextProvider;
import com.gruppo1.smarthome.command.api.CrudOperation;
import com.gruppo1.smarthome.model.SmartHomeItem;
import com.gruppo1.smarthome.repository.BaseSmartHomeRepository;

import java.util.List;

public class GetOperationImpl implements CrudOperation {
    @Override
    public List<SmartHomeItem> execute(Object homeItemPlaceholder) {
        BaseSmartHomeRepository repository = ApplicationContextProvider.getRepository(homeItemPlaceholder);
        assert repository != null;
        return (List<SmartHomeItem>) repository.findAll();
    }
}
