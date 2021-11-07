package com.gruppo1.smarthome.model;

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
    private String id;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false)
    private Boolean status;
    @Column(nullable = false)
    private String type;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = true)
    private Room room;

    @OneToMany(mappedBy = "device", cascade = CascadeType.ALL)
    private List<Conditions> conditions;

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
}
