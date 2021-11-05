package com.gruppo1.smarthome.crud.api;

public interface CrudOperation {
    default Object execute(Object s) {
        return null;
    }

    default Object execute(Object s, String itemName) {
        return null;
    }
}
