package com.gruppo1.smarthome.model;

import javax.persistence.*;

@Entity
public class Conditioner extends Device {

    @Id
    @Column(nullable = false, updatable = false)
    private String id;
    private Integer temperature;
    private String settings;


    public void setTemperature(Integer temperature) {this.temperature = temperature;}

    public void setSettings(String settings) {this.settings = settings;}

    public Integer getTemperature(){return temperature;}

    public String getSettings(){return settings;}

}
