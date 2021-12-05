package com.gruppo1.smarthome.command.api;

import com.gruppo1.smarthome.model.SmartHomeItem;
import com.gruppo1.smarthome.repository.BaseSmartHomeRepository;

import java.util.List;

public interface CrudOperation {
    default List<SmartHomeItem> execute() {
        return null;
    }

    default List<SmartHomeItem> execute(String homeItemName) {
        return null;
    }

    default List<SmartHomeItem> execute(SmartHomeItem homeItem) {
        return null;
    }

    default int executeDelete(SmartHomeItem homeItem) {
        return 0;
    }

    BaseSmartHomeRepository getRepository();

}
