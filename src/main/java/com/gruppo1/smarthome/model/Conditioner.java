package com.gruppo1.smarthome.model;

import org.apache.commons.lang.StringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Conditioner extends Device {

    @Id
    @Column(nullable = false, updatable = false)
    private String id;
    private Integer temperature;
    private String settings;

    public Conditioner() {
        super(StringUtils.EMPTY);
    }

    public void setTemperature(Integer temperature) {this.temperature = temperature;}

    public void setSettings(String settings) {this.settings = settings;}

    public Integer getTemperature(){return temperature;}

    public String getSettings(){return settings;}

}
