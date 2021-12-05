package com.gruppo1.smarthome.command.impl;

import com.gruppo1.smarthome.command.api.CrudOperation;
import com.gruppo1.smarthome.model.SmartHomeItem;
import com.gruppo1.smarthome.repository.BaseSmartHomeRepository;
import com.gruppo1.smarthome.repository.ConditionRepo;

import java.util.List;
import java.util.Objects;

public class GetConditionsByDeviceName implements CrudOperation {

    private final ConditionRepo repository;

    public GetConditionsByDeviceName(ConditionRepo repository) {
        this.repository = repository;
    }

    @Override
    public List<SmartHomeItem> execute(String deviceName) {
        if (Objects.isNull(repository))
            return null;
        List<SmartHomeItem> conditions = repository.findConditionsByDeviceName(deviceName);
        return Objects.nonNull(conditions) ?
                conditions : null;
    }

    @Override
    public BaseSmartHomeRepository getRepository() {
        return repository;
    }

}
