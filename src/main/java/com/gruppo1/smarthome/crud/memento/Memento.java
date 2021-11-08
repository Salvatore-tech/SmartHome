package com.gruppo1.smarthome.crud.memento;

import com.gruppo1.smarthome.crud.api.CrudOperation;

public class Memento implements CrudOperation {

    private CrudOperation operation;

    public Memento(CrudOperation operation) {
        this.operation = operation;
    }

    public CrudOperation getOperation() {
        return operation;
    }
}
