package com.gruppo1.smarthome.model;
import javax.persistence.*;
import java.io.Serializable;


@Entity
public class Device implements Serializable {

    @Id
    @Column(nullable = false, updatable = false)
    private String id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = true)
    private Room room;

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

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
