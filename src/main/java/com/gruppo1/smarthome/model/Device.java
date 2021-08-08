package com.gruppo1.smarthome.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Device implements Serializable {
    @Id
    @Column(nullable = false, updatable = false)
    private long id;

}
