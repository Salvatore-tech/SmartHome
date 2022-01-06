package com.gruppo1.smarthome.memento;

import com.gruppo1.smarthome.command.api.CrudOperation;
import com.gruppo1.smarthome.command.impl.AddOperationImpl;
import com.gruppo1.smarthome.command.impl.DeleteOperationImpl;
import com.gruppo1.smarthome.command.impl.UpdateOperationImpl;
import com.gruppo1.smarthome.model.MappingOriginatorMemento;
import com.gruppo1.smarthome.model.SmartHomeItem;
import com.gruppo1.smarthome.repository.BaseSmartHomeRepository;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class MementoCareTaker {
    private final List<ImmutablePair<CrudOperation, Memento>> mementoPairList = new ArrayList<>();


    public void push(CrudOperation operation, Memento memento) {
        mementoPairList.add(new ImmutablePair<>(operation, memento));
    }

    public int undo() {
        ImmutablePair<CrudOperation, Memento> lastMementoPair = getUndo();
        if (Objects.nonNull(lastMementoPair))
            if (Objects.nonNull(executeUndoOperation(lastMementoPair)))
                return 1;
        return 0;
    }

    private ImmutablePair<CrudOperation, Memento> getUndo() {
        if (mementoPairList.isEmpty())
            return null;
        return mementoPairList.get(mementoPairList.size() - 1);
    }

    private <Any> Any executeUndoOperation(ImmutablePair<CrudOperation, Memento> lastMementoPair) {
        CrudOperation operationReverted;
        CrudOperation lastOperationPerformed = lastMementoPair.getLeft();
        Memento lastMemento = lastMementoPair.getRight();
        BaseSmartHomeRepository repository = lastOperationPerformed.getRepository();

        if (lastOperationPerformed instanceof AddOperationImpl) {
            operationReverted = new DeleteOperationImpl(repository);
        } else if (lastOperationPerformed instanceof DeleteOperationImpl) {
            operationReverted = new AddOperationImpl(repository);
        } else if (lastOperationPerformed instanceof UpdateOperationImpl) {
            operationReverted = new UpdateOperationImpl(repository);
        } else
            return null;

        MappingOriginatorMemento mappingOriginatorMemento = new MappingOriginatorMemento();
        SmartHomeItem originator = mappingOriginatorMemento.getOriginator(lastMemento.getClass().toString());
        SmartHomeItem restoredItem = originator.restore(lastMemento);
        mementoPairList.add(new ImmutablePair<>(operationReverted, restoredItem.createMemento()));
        return operationReverted.execute(restoredItem);
    }

    public List<ImmutablePair<CrudOperation, Memento>> getMementoCommandList() {
        return mementoPairList;
    }
}
