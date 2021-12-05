package com.gruppo1.smarthome.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gruppo1.smarthome.memento.Memento;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Device extends SmartHomeItem implements Serializable {

    @JsonIgnore
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(nullable = false, updatable = false)
    protected String id;
    @Column(nullable = false, unique = true)
    protected String name;
    @Column(nullable = false)
    protected Boolean status;
    @Column(nullable = false, updatable = false)
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

    @Override
    public String getName(){
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

    @Override
    public Memento createMemento() {
        return new MementoDevice();
    }

    protected class MementoDevice extends Memento {

        private String memId;
        private String memName;
        private Boolean memStatus;
        private String memType;
        private Room memRoom;
        private List<Condition> memConditions;

        public MementoDevice() {
            this.memId = id;
            this.memName = name;
            this.memStatus = status;
            this.memType = type;
            this.memRoom = room;
            this.memConditions = conditions;
        }

        @Override
        public String getName() {
            return memName;
        }
    }
}
