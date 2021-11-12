package com.gruppo1.smarthome.crud.api;

public interface CrudOperation {
    default Object execute(Object s) {
        return null;
    }
    default Object execute(Object s, String itemName) {
        return null;
    }
    // TODO: SS
//    default Memento generateMemento(){
//        return new Memento(this);
//    }
}
