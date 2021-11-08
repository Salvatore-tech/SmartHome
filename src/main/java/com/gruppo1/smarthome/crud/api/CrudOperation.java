package com.gruppo1.smarthome.crud.api;

import com.gruppo1.smarthome.crud.memento.Memento;

public interface CrudOperation {

    default Object execute(Object s) {
        return null;
    }
    default Object execute(Object s, String itemName) {
        return null;
    }
    default Memento generateMemento(){
        return new Memento(this);
    }
}
