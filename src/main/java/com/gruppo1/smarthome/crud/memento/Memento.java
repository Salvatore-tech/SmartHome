package com.gruppo1.smarthome.crud.memento;

import com.gruppo1.smarthome.crud.api.CrudOperation;
import com.gruppo1.smarthome.model.SmartHomeItem;

public class Memento implements CrudOperation {

    private CrudOperation operation;
    private SmartHomeItem homeItem;
    private String desc;

    public Memento(CrudOperation operation, SmartHomeItem homeItem, String description) {
        this.operation = operation;
        this.homeItem = homeItem;
        this.desc = description;
    }

    public CrudOperation getOperation() {
        return operation;
    }

    public SmartHomeItem getHomeItem() {
        return homeItem;
    }

    public String getHomeItemName() {
        return homeItem.getName();
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
