package com.gruppo1.smarthome.command.api;

public interface CrudOperation {
    default Object execute(Object homeItemPlaceholder) {
        return null;
    }

    default Object execute(Object s, String itemName) {
        return null;
    }
}
