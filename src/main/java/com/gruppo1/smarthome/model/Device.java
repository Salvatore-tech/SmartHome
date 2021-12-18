package com.gruppo1.smarthome.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "device")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Device extends SmartHomeItem implements Serializable {

    @Column(nullable = false, updatable = false)
    protected String type;
    @Column
    protected Boolean status;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "room_id", nullable = true)
    protected Room room;

    @JsonIgnore
    @OneToMany(mappedBy = "device", cascade = CascadeType.ALL)
    protected List<Condition> conditions;

    public Device() {
        // do not remove
    }

    public Device(String name, String type, Boolean status) {
        this.name = name;
        this.status = status;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public void addCondition(Condition condition) {
        this.conditions.add(condition);
    }

    public List<Condition> getConditions() {
        return this.conditions;
    }

    @Override
    public String toString() {
        return "Device{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", type='" + type + '\'' +
                ", room=" + room +
                '}';
    }
}
