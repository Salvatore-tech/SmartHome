package com.gruppo1.smarthome.model;

import java.io.Serializable;

//@MappedSuperclass
public abstract class SmartHomeItem implements Serializable {

    protected String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
