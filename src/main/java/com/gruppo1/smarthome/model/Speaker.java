package com.gruppo1.smarthome.model;

import org.apache.commons.lang.StringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Speaker extends Device{

    @Id
    @Column(nullable = false, updatable = false)
    private String id;
    private Integer volume;

    public Speaker() {
        super(StringUtils.EMPTY);
    }

    public void setVolume(Integer volume) {this.volume = volume;}
    public Integer getVolume(){return volume;}
}
