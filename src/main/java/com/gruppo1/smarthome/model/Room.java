package com.gruppo1.smarthome.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gruppo1.smarthome.memento.Memento;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Entity
public class Room extends SmartHomeItem implements Serializable {

    @JsonIgnore
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(nullable = false, updatable = false)
    private String id;

    @Column(nullable = false, unique = true)
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "room")
    private List<Device> devices;

    public Room() {}

    public Room(String name) {
        this.name = name;
    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Memento createMemento() {
        return new MementoRoom();
    }

    public List<Device> getDevices() {
        return this.devices;
    }

    public void addDevice(Device device) {
        this.devices.add(device);
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


    class MementoRoom extends Memento {

        private String memId;
        private String memName;
        private List<Device> memDevices;

        public MementoRoom() {
            this.memId = id;
            this.memName = name;
            this.memDevices = devices;
        }

        @Override
        public String getName() {
            return memName;
        }

    }
}
