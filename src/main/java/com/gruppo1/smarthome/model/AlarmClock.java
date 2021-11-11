package com.gruppo1.smarthome.model;

import org.apache.commons.lang.StringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class AlarmClock extends Device {

    @Id
    @Column(nullable = false, updatable = false)
    private String id;
    private String time;

    public AlarmClock() {
        super(StringUtils.EMPTY);
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

}
