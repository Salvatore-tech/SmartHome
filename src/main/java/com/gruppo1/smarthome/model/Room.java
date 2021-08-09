package com.gruppo1.smarthome.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;


@Entity
public class Room implements Serializable {

    @Id
    @Column(nullable = false, updatable = false)
    private Long id;
    @Column(nullable = false)
    private String name;

    @OneToMany
    private Device id_device;

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }
}
