package com.gruppo1.smarthome.model;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Entity
public class Room implements Serializable {

    @Id
    @Column(name = "id")
    private String id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY)
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
}
