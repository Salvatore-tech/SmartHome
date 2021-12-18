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
    }

    public Room(String name) {
        this.name = name;
    }

    public List<Device> getDevices() {
        return this.devices;
    }

    public void addDevice(List<Device> devices) {
        if (Objects.isNull(devices) || devices.isEmpty())
            return;
        devices.forEach(device -> this.devices.add(device));
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
        return new MementoRoom(id, name, devices);
    }

    @Override
    public SmartHomeItem restore(Memento memento) {
        MementoRoom mementoRoom = (MementoRoom) memento;
        Room room = new Room();
        room.id = mementoRoom.getId();
        room.name = mementoRoom.getName();
        room.devices = mementoRoom.devices;
        return room;
    }

    class MementoRoom extends Memento {

        private List<Device> devices;

        public MementoRoom() {
            // do not remove
        }

        public MementoRoom(String id, String name, List<Device> devices) {
            this.id = id;
            this.name = name;
            this.devices = devices;
        }
    }
}
