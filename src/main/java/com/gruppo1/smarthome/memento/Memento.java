package com.gruppo1.smarthome.memento;

public abstract class Memento {
    protected String id;
    protected String name;
    protected String label;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLabel() { return label; }
}
