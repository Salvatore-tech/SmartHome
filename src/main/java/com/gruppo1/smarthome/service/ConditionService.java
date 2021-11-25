package com.gruppo1.smarthome.service;

import com.gruppo1.smarthome.beans.CrudOperationExecutor;
import com.gruppo1.smarthome.command.impl.*;
import com.gruppo1.smarthome.model.Condition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ConditionService {
    private final CrudOperationExecutor operationExecutor;

    @Autowired
    public ConditionService(CrudOperationExecutor operationExecutor) {
        this.operationExecutor = operationExecutor;
    }

    public Condition addCondition(Condition condition) {
        return (Condition) operationExecutor.execute(new AddOperationImpl(), condition);
    }

    public List<Condition> findAllConditions() {
        return (List<Condition>) operationExecutor.execute(new GetOperationImpl(), this);
    }

    public Condition findConditionsByName(String name) {
        return (Condition) operationExecutor.execute(new GetByNameOperationImpl(), name);
    }

    public Condition updateCondition(Condition condition) {
        return (Condition) operationExecutor.execute(new UpdateOperationImpl(), condition);
    }

    public Integer deleteCondition(String name) {
        return (Integer) operationExecutor.execute(new DeleteOperationImpl(), name, this);
    }

}
