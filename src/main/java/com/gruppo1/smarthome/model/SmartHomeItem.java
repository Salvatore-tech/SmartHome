package com.gruppo1.smarthome.model;

import com.gruppo1.smarthome.memento.Memento;

import java.io.Serializable;

public abstract class SmartHomeItem implements Serializable {

    public abstract String getName();

    public void setName(String name) {
    }

    ;

    public abstract Memento createMemento();

}
