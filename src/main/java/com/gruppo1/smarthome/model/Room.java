package com.gruppo1.smarthome.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gruppo1.smarthome.memento.Memento;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "room")
public class Room extends SmartHomeItem implements Serializable {

    @JsonIgnore
    @OneToMany(mappedBy = "room", fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    private List<Device> devices;

    public Room() {
        this.label = "Room";
    }

    public Room(String name) {
        this.label = "Room";
        this.name = name;
    }

    public List<Device> getDevices() {
        return this.devices;
    }

    public void addDevice(List<Device> devices) {
        if (Objects.isNull(devices) || devices.isEmpty())
            return;
        this.devices.addAll(devices);
    }

    public void removeDevice(Device device) {
        this.devices.remove(device);
    }

    @Override
    public String toString() {
        return "Room{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public Memento createMemento() {
        return new MementoRoom(id, label, name, devices);
    }

    @Override
    public SmartHomeItem restore(Memento memento) {
        MementoRoom mementoRoom = (MementoRoom) memento;
        this.id = mementoRoom.getId();
        this.name = mementoRoom.getName();
        this.devices = mementoRoom.devices;
        return this;
    }

    static class MementoRoom extends Memento {

        private final List<Device> devices;

        public MementoRoom(String id, String label, String name, List<Device> devices) {
            this.id = id;
            this.label = label;
            this.name = name;
            this.devices = devices;
        }
    }
}
