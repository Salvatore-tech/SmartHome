package com.gruppo1.smarthome.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Scene implements Serializable {

    @Id
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Boolean status;

    @OneToMany(mappedBy = "scene", cascade = CascadeType.ALL)
    private List<Conditions> conditions;

    public String getId(){
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public Boolean getStatus(){
        return status;
    }

    public void setStatus(Boolean status){
        this.status = status;
    }

}
