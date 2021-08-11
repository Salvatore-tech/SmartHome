package com.gruppo1.smarthome.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.io.Serializable;
import java.util.List;

@Entity
public class Device implements Serializable {
    @Id
    @Column(nullable = false, updatable = false)
    private long id;

    @ManyToMany(mappedBy = "deviceList")
    List<Scene> sceneList;

}
