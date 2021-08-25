package com.gruppo1.smarthome.service;


import com.gruppo1.smarthome.model.Conditions;
import com.gruppo1.smarthome.repository.ConditionsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class ConditionsService {
    private final ConditionsRepo conditionsRepo;

    @Autowired
    public ConditionsService(ConditionsRepo conditionsRepo) {
        this.conditionsRepo = conditionsRepo;
    }

    public Conditions addConditions(Conditions condition) {
        return conditionsRepo.save(condition);
    }

    public List<Conditions> findAllCondition() {
        return (List<Conditions>) conditionsRepo.findAll();
    }

    public Optional<Conditions> findConditionsById(Conditions condition) {
        return conditionsRepo.findById(condition.getConditionId());
    }

    public Conditions updateConditions(Conditions conditions) {
        return conditionsRepo.save(conditions);
    }

    public void deleteConditions(Conditions conditions) {
        conditionsRepo.deleteById(conditions.getConditionId());
    }

}
