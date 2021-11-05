package com.gruppo1.smarthome.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Scene extends SmartHomeItem implements Serializable {

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(nullable = false, updatable = false)
    private String id;

    @Column(nullable = false, unique = true)
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

    public void addCondition(Conditions condition){this.conditions.add(condition);}

    public void removeCondition(Conditions condition){this.conditions.remove(condition);}

}
