package com.gruppo1.smarthome.command.impl;

import com.gruppo1.smarthome.command.api.CrudOperation;
import com.gruppo1.smarthome.model.Condition;
import com.gruppo1.smarthome.repository.ConditionRepo;

import java.util.List;
import java.util.Objects;

public class GetConditionsByDeviceName extends CrudOperation {

    public GetConditionsByDeviceName(ConditionRepo repository) {
        super(repository);
    }

    @Override
    public List<Condition> execute(String deviceName) {
        if (Objects.isNull(repository))
            return null;
        return ((ConditionRepo) repository).findConditionsByDeviceName(deviceName);
    }
}
