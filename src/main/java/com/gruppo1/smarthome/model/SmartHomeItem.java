package com.gruppo1.smarthome.model;

import java.io.Serializable;
import java.time.LocalDateTime;

//@MappedSuperclass
public abstract class SmartHomeItem implements Serializable {
//    @Id
//    @GeneratedValue
//    protected String id;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String name; // TODO


    public SmartHomeItem() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    //    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
