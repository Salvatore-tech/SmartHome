package com.gruppo1.smarthome.model;

import java.io.Serializable;

//@MappedSuperclass
public abstract class SmartHomeItem implements Serializable {

    protected String id;
    protected String name;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
