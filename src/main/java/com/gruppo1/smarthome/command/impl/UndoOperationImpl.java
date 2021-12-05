package com.gruppo1.smarthome.command.impl;

import com.gruppo1.smarthome.command.api.CrudOperation;
import com.gruppo1.smarthome.model.SmartHomeItem;
import com.gruppo1.smarthome.repository.BaseSmartHomeRepository;

import java.util.List;

//TODO SS: to implement
public class UndoOperationImpl implements CrudOperation {

    private BaseSmartHomeRepository repository;

    @Override
    public List<SmartHomeItem> execute(SmartHomeItem homeItemPlaceholder) {
//        Memento lastMementoOperation = ApplicationContextProvider.getApplicationContext().getBean(MementoCareTaker.class).getLastMementoOperation();
//        BaseSmartHomeRepository repository = ApplicationContextProvider.getRepository(lastMementoOperation.getHomeItem());
//        if (Objects.isNull(repository))
//            return null;
//        return computeRevertedOperation(lastMementoOperation.getOperation(), repository, lastMementoOperation.getHomeItem());
        return null;
    }

    //    CrudOperation computeRevertedOperation(CrudOperation operationToRevert, BaseSmartHomeRepository repository, SmartHomeItem item) {
//        CrudOperation operationReverted;
//        if (operationToRevert.getClass().equals(AddOperationImpl.class)) {
////            operationReverted = new DeleteOperationImpl();
//            repository.deleteByName(item.getName());
//        } else if (operationToRevert.getClass().equals(DeleteOperationImpl.class)) {
////            operationReverted = new AddOperationImpl();
//            repository.save(item);
//        } else
//            operationReverted = null;
//        return operationReverted;
//    }
    @Override
    public BaseSmartHomeRepository getRepository() {
        return repository;
    }

}
