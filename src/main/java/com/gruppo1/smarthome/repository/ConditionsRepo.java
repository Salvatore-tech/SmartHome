package com.gruppo1.smarthome.repository;

import com.gruppo1.smarthome.model.ConditionId;
import com.gruppo1.smarthome.model.Conditions;
import org.springframework.data.repository.CrudRepository;

public interface ConditionsRepo extends CrudRepository<Conditions, ConditionId> {
    void deleteById(ConditionId conditionId);
}
