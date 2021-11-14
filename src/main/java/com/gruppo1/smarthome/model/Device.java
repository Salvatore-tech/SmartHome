package com.gruppo1.smarthome.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Device extends SmartHomeItem implements Serializable  {

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(nullable = false, updatable = true)
    protected String id;
    @Column(nullable = false, unique = true)
    protected String name;
    @Column(nullable = false)
    protected Boolean status;
    @Column(nullable = false)
    protected String type;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "room_id", nullable = true)
    protected Room room;

    @JsonIgnore
    @OneToMany(mappedBy = "device", cascade = CascadeType.ALL)
    protected List<Condition> conditions;


    public Device() {
    }

    public Device(String name) {
        this.name = name;
    }


    public Device(String name, String type) {
        this.name = name;
        this.status = false;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName(){
        return name;
    }

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

    public void addCondition(Condition condition){ this.conditions.add(condition); }

    public List<Condition> getConditions() { return this.conditions; }

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
