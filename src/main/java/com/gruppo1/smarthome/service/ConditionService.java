package com.gruppo1.smarthome.service;

import com.gruppo1.smarthome.beans.CrudOperationExecutor;
import com.gruppo1.smarthome.command.api.CrudOperation;
import com.gruppo1.smarthome.command.impl.*;
import com.gruppo1.smarthome.memento.Memento;
import com.gruppo1.smarthome.memento.MementoCareTaker;
import com.gruppo1.smarthome.model.Condition;
import com.gruppo1.smarthome.model.SmartHomeItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class ConditionService {
    private final CrudOperationExecutor operationExecutor;
    private final MementoCareTaker mementoCareTaker;

    @Autowired
    public ConditionService(CrudOperationExecutor operationExecutor, MementoCareTaker mementoCareTaker) {
        this.operationExecutor = operationExecutor;
        this.mementoCareTaker = mementoCareTaker;
    }

    public Condition addCondition(Condition condition) {
        CrudOperation operationToPerform = new AddOperationImpl();
        if (!isPresent((Condition) operationExecutor.execute(new GetByNameOperationImpl(), condition.getName(), this))) {
            mementoCareTaker.add(new Memento(operationToPerform, condition, "Add a condition"));
            return (Condition) operationExecutor.execute(new AddOperationImpl(), condition);
        }
        return null;
    }

    public List<Condition> findAllConditions() {
        return (List<Condition>) operationExecutor.execute(new GetOperationImpl(), this);
    }

    public Condition findConditionsByName(String name) {
        CrudOperation operationToPerform = new GetByNameOperationImpl();
        Condition result = (Condition) operationExecutor.execute(operationToPerform, name, this);
        mementoCareTaker.add(new Memento(operationToPerform, result, "Find a profile given a name"));
        return result;
    }

    public Integer deleteCondition(String name) {
        CrudOperation operationToPerform = new DeleteOperationImpl();
        Condition condition = (Condition) operationExecutor.execute(new GetByNameOperationImpl(), name, this);
        if (isPresent(condition)) {
            mementoCareTaker.add(new Memento(operationToPerform, condition, "Delete a condition"));
            return (Integer) operationExecutor.execute(operationToPerform, name, this);
        }
        return 0;
    }

    private boolean isPresent(SmartHomeItem item) {
        return Objects.nonNull(item);
    }

}
