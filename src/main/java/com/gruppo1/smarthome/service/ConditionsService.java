package com.gruppo1.smarthome.service;

import com.gruppo1.smarthome.crud.beans.CrudOperationExecutor;
import com.gruppo1.smarthome.crud.impl.GetByNameOperationImpl;
import com.gruppo1.smarthome.crud.impl.GetOperationImpl;
import com.gruppo1.smarthome.crud.impl.AddOperationImpl;
import com.gruppo1.smarthome.crud.impl.UpdateOperationImpl;
import com.gruppo1.smarthome.crud.impl.DeleteOperationImpl;
import com.gruppo1.smarthome.model.Conditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;


@Service
@Transactional
public class ConditionsService {
    private final CrudOperationExecutor operationExecutor;

    @Autowired
    public ConditionsService(CrudOperationExecutor operationExecutor) {
        this.operationExecutor = operationExecutor;
    }

    public Conditions addConditions(Conditions condition) {
        return (Conditions) operationExecutor.execute(new AddOperationImpl(), condition);
    }

    public List<Conditions> findAllCondition() {
        return (List<Conditions>) operationExecutor.execute(new GetOperationImpl(), this);
    }

    public Conditions findConditionsByName(String name) {
        return (Conditions) operationExecutor.execute(new GetByNameOperationImpl(), name);
    }

    public Conditions updateConditions(Conditions conditions) {
        return (Conditions) operationExecutor.execute(new UpdateOperationImpl(), conditions);
    }

    public Integer deleteConditions(String name) {
        return (Integer) operationExecutor.execute(new DeleteOperationImpl(), name, this);
    }

}
