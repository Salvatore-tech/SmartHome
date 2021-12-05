package com.gruppo1.smarthome.service;

import com.gruppo1.smarthome.command.api.CrudOperation;
import com.gruppo1.smarthome.command.impl.AddOperationImpl;
import com.gruppo1.smarthome.command.impl.DeleteOperationImpl;
import com.gruppo1.smarthome.command.impl.GetByNameOperationImpl;
import com.gruppo1.smarthome.command.impl.GetOperationImpl;
import com.gruppo1.smarthome.memento.MementoCareTaker;
import com.gruppo1.smarthome.model.Condition;
import com.gruppo1.smarthome.model.SmartHomeItem;
import com.gruppo1.smarthome.repository.ConditionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class ConditionService {
    private final ConditionRepo conditionRepo;
    private final MementoCareTaker mementoCareTaker;

    @Autowired
    public ConditionService(ConditionRepo conditionRepo, MementoCareTaker mementoCareTaker) {
        this.conditionRepo = conditionRepo;
        this.mementoCareTaker = mementoCareTaker;
    }

    public Condition addCondition(Condition condition) {
        CrudOperation addConditionOperation = new AddOperationImpl(conditionRepo);
        CrudOperation getConditionOperation = new GetByNameOperationImpl(conditionRepo);
        if (!isPresent((Condition) getConditionOperation.execute(condition.getName()))) {
            mementoCareTaker.push(addConditionOperation, condition.createMemento());
            return (Condition) addConditionOperation.execute(condition).get(0);
        }
        return null;
    }

    public List<Condition> findAllConditions() {
        CrudOperation getAllConditionsOperation = new GetOperationImpl(conditionRepo);
        mementoCareTaker.push(getAllConditionsOperation, null);
        return (List<Condition>) (List<?>) getAllConditionsOperation.execute();
    }

    public Condition findConditionsByName(String name) {
        CrudOperation getConditionOperation = new GetByNameOperationImpl(conditionRepo);
        Condition result = (Condition) getConditionOperation.execute(name);
        mementoCareTaker.push(getConditionOperation, null); //TODO SS
        return result;
    }

    public Condition deleteCondition(String conditionName) {
        CrudOperation getConditionOperation = new GetByNameOperationImpl(conditionRepo);
        CrudOperation deleteConditionOperation = new DeleteOperationImpl(conditionRepo);
        Condition condition = (Condition) getConditionOperation.execute(conditionName);
        mementoCareTaker.push(deleteConditionOperation, condition.createMemento());
        if (isPresent(condition))
            return (Condition) deleteConditionOperation.execute(condition).get(0);
        return null;
    }

    private boolean isPresent(SmartHomeItem item) {
        return Objects.nonNull(item);
    }

}
