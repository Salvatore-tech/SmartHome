package com.gruppo1.smarthome.model;

import javax.persistence.*;

@Entity
public class AlarmClock extends Device {

    @Id
    @Column(nullable = false, updatable = false)
    private String id;
    private String time;

    public void setTime(String time) {this.time = time;}

    public String getTime(){return time;}

}
