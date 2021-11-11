package com.gruppo1.smarthome.model;

import org.apache.commons.lang.StringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Television extends Device {

    @Id
    @Column(nullable = false, updatable = false)
    private String id;
    private Integer volume;
    private Integer channel;

    public Television() {
        super(StringUtils.EMPTY);
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }

    public Integer getVolume() {
        return volume;
    }

    public Integer getChannel() {
        return channel;
    }

}
