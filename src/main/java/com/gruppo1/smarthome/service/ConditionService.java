package com.gruppo1.smarthome.service;


import com.gruppo1.smarthome.model.Condition;
import com.gruppo1.smarthome.repository.ConditionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class ConditionService {
    private final ConditionRepo conditionRepo;

    @Autowired
    public ConditionService(ConditionRepo conditionRepo) {
        this.conditionRepo = conditionRepo;
    }

    public Condition addCondition(Condition condition) {
        return conditionRepo.save(condition);
    }

    public List<Condition> findAllConditions(){return (List<Condition>) conditionRepo.findAll(); }

    public Optional<Condition> findConditionById(Condition condition){
        return conditionRepo.findById(condition.getConditionId());
    }

    public Condition updateCondition(Condition condition) {
        return conditionRepo.save(condition);
    }

    public void deleteCondition(Condition condition){
        conditionRepo.deleteById(condition.getConditionId());
    }

}
