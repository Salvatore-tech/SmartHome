package com.gruppo1.smarthome.crud.impl;

import com.gruppo1.smarthome.crud.api.BaseSmartHomeRepository;
import com.gruppo1.smarthome.crud.api.CrudOperation;
import com.gruppo1.smarthome.crud.beans.ApplicationContextProvider;
import com.gruppo1.smarthome.crud.memento.Memento;
import com.gruppo1.smarthome.crud.memento.MementoCareTaker;
import com.gruppo1.smarthome.model.SmartHomeItem;
import org.apache.commons.lang.StringUtils;

import java.util.Objects;

public class UndoOperationImpl implements CrudOperation {

    @Override
    public CrudOperation execute(Object item) {

        Memento lastMementoOperation = ApplicationContextProvider.getApplicationContext().getBean(MementoCareTaker.class).getLastMementoOperation();
        BaseSmartHomeRepository repository = ApplicationContextProvider.getRepository(lastMementoOperation.getHomeItem());
        if (Objects.isNull(repository))
            return null;
        return computeRevertedOperation(lastMementoOperation.getOperation(), repository, lastMementoOperation.getHomeItem());
    }

    CrudOperation computeRevertedOperation(CrudOperation operationToRevert, BaseSmartHomeRepository repository, SmartHomeItem item) {

        CrudOperation operationReverted;
        if (operationToRevert.getClass().equals(AddOperationImpl.class)){
            operationReverted = new DeleteOperationImpl();
            repository.deleteByName(item.getName());
        }
        else if (operationToRevert.getClass().equals(DeleteOperationImpl.class)){

            operationReverted = new AddOperationImpl();
            repository.save(item);

        }
        else
            operationReverted = null;
        return operationReverted;
    }
}
