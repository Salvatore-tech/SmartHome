package com.gruppo1.smarthome.model;

import javax.persistence.*;

@Entity
public class Speaker extends Device{

    @Id
    @Column(nullable = false, updatable = false)
    private String id;
    private Integer volume;

    public void setVolume(Integer volume) {this.volume = volume;}
    public Integer getVolume(){return volume;}
}
