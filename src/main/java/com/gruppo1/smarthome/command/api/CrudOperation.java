package com.gruppo1.smarthome.command.api;

import com.gruppo1.smarthome.model.SmartHomeItem;
import com.gruppo1.smarthome.repository.BaseSmartHomeRepository;

import java.util.List;

public abstract class CrudOperation {
    protected final BaseSmartHomeRepository repository;

    public CrudOperation(BaseSmartHomeRepository repository) {
        this.repository = repository;
    }

    public List<SmartHomeItem> execute() {
        return null;
    }

    public <Any> Any execute(String homeItemName) {
        return null;
    }

    public <Any> Any execute(SmartHomeItem homeItem) {
        return null;
    }

    public BaseSmartHomeRepository getRepository() {
        return repository;
    }

}
