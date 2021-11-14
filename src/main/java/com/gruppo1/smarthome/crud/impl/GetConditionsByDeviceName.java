package com.gruppo1.smarthome.crud.impl;

import com.gruppo1.smarthome.crud.api.CrudOperation;
import com.gruppo1.smarthome.crud.beans.ApplicationContextProvider;
import com.gruppo1.smarthome.model.Condition;
import com.gruppo1.smarthome.repository.ConditionRepo;

import java.util.List;
import java.util.Objects;

public class GetConditionsByDeviceName implements CrudOperation{
        @Override
        public List<Condition> execute(Object item, String deviceName) {
            ConditionRepo repository = (ConditionRepo) ApplicationContextProvider.getRepository(item);
            assert repository != null;
            return Objects.nonNull(repository.findConditionsByDeviceName(deviceName)) ?
                    repository.findConditionsByDeviceName(deviceName) : null;
        }
}
