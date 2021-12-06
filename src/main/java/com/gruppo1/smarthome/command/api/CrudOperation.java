package com.gruppo1.smarthome.command.api;

import com.gruppo1.smarthome.model.SmartHomeItem;
import com.gruppo1.smarthome.repository.BaseSmartHomeRepository;

import java.util.List;

public interface CrudOperation {
    default List<SmartHomeItem> execute() {
        return null;
    }

    default <Any> Any execute(String homeItemName) {
        return null;
    }

    default <Any> Any execute(SmartHomeItem homeItem) {
        return null;
    }

    BaseSmartHomeRepository getRepository();

}
