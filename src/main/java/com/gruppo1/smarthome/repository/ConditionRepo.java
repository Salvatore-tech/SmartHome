package com.gruppo1.smarthome.repository;
import com.gruppo1.smarthome.model.Condition;
import com.gruppo1.smarthome.model.ConditionId;
import org.springframework.data.repository.CrudRepository;

public interface ConditionRepo extends CrudRepository<Condition, ConditionId> {
    void deleteById(ConditionId conditionId);
}
