package com.gruppo1.smarthome.memento;

import com.gruppo1.smarthome.model.Room;

public abstract class MementoDevice extends Memento {

    protected final String type;
    protected final Boolean status;
    protected final Room room;

    public MementoDevice(String id, String label, String name, Boolean status, String type, Room room) {
        this.id = id;
        this.label = label;
        this.name = name;
        this.status = status;
        this.type = type;
        this.room = room;
    }

    public String getType() {
        return type;
    }

    public Boolean getStatus() {
        return status;
    }

    public Room getRoom() {
        return room;
    }
}
