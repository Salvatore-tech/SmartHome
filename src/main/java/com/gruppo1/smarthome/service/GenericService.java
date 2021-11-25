package com.gruppo1.smarthome.service;

import com.gruppo1.smarthome.beans.CrudOperationExecutor;
import com.gruppo1.smarthome.command.api.CrudOperation;
import com.gruppo1.smarthome.command.impl.UndoOperationImpl;
import com.gruppo1.smarthome.memento.Memento;
import com.gruppo1.smarthome.memento.MementoCareTaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class GenericService {
    private final CrudOperationExecutor operationExecutor;
    private MementoCareTaker mementoCareTaker;

    @Autowired
    public GenericService(CrudOperationExecutor operationExecutor, MementoCareTaker mementoCareTaker) {
        this.operationExecutor = operationExecutor;
        this.mementoCareTaker = mementoCareTaker;
    }

    public CrudOperation undo(){
        CrudOperation operationToPerform = new UndoOperationImpl();
        mementoCareTaker.add(new Memento(operationToPerform, null, "Undo last operation"));
        return (CrudOperation) operationExecutor.execute(operationToPerform, this);
    }

    public List<Memento> getHistory(){
        return mementoCareTaker.getMementoList();
    }

}
