package com.gruppo1.smarthome.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Entity
public class Room implements Serializable {

    @JsonIgnore
    @Id
    @Column(updatable = false)
    private String id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "room")
    private List<Device> devices;

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public void addDevice(Device device) {this.devices.add(device);}

    public void removeDevice(Device device) { this.devices.remove(device); }

}
