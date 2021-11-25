package com.gruppo1.smarthome.command.impl;

import com.gruppo1.smarthome.beans.ApplicationContextProvider;
import com.gruppo1.smarthome.command.api.CrudOperation;
import com.gruppo1.smarthome.model.Condition;
import com.gruppo1.smarthome.repository.ConditionRepo;

import java.util.List;
import java.util.Objects;

public class GetConditionsByDeviceName implements CrudOperation {

    @Override
    public List<Condition> execute(Object item, String deviceName) {
        ConditionRepo repository = (ConditionRepo) ApplicationContextProvider.getRepository(item);
        assert repository != null;
        List<Condition> conditions = repository.findConditionsByDeviceName(deviceName);
        return Objects.nonNull(conditions) ?
                conditions : null;
    }
}
