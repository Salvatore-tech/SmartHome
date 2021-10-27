package com.gruppo1.smarthome.model;

import java.util.ArrayList;

public class AlarmClock extends Device {
    private String time;
    private ArrayList timeToAlarm;

    public void setTime(String time) {this.time = time;}

    public void addTimeToAlarm(String time) {this.timeToAlarm.add(time);}

    public void removeTimeToAlarm(String time) {this.timeToAlarm.remove(time);}

    public String getTime(){return time;}

    public ArrayList getTimeToAlarm(){return this.timeToAlarm;}

}
