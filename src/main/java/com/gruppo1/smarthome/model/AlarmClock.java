package com.gruppo1.smarthome.model;

import com.gruppo1.smarthome.memento.Memento;
import com.gruppo1.smarthome.memento.MementoDevice;
import org.apache.commons.lang.StringUtils;

import javax.persistence.Entity;
import java.util.Objects;

@Entity
public class AlarmClock extends Device {

    private String time;
    private String frequency;
    private String song;

    public AlarmClock() {
        // do not remove
    }

    public AlarmClock(String type) {
        this.type = type;
    }

    public AlarmClock(String name, String type, Boolean status, String time, String frequency, String song) {
        super(name, type, status);
        this.time = time;
        this.frequency = frequency;
        this.song = song;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }

    @Override
    public String toString() {
        return "AlarmClock{" +
                "name='" + name + '\'' +
                ", status=" + status +
                ", type='" + type + '\'' +
                ", room=" + (Objects.nonNull(room) ? room.getName() : StringUtils.EMPTY) + '\'' +
                ", time='" + time + '\'' +
                ", frequency='" + frequency + '\'' +
                ", song='" + song + '\'' +
                '}';
    }

    @Override
    public Memento createMemento() {
        return new MementoAlarmClock(id, name, status, type, room, time, frequency, song);
    }

    @Override
    public SmartHomeItem restore(Memento memento) {
        AlarmClock alarmClock = new AlarmClock();
        MementoAlarmClock mementoAlarmClock = (MementoAlarmClock) memento;
        alarmClock.id = mementoAlarmClock.getId();
        alarmClock.name = mementoAlarmClock.getName();
        alarmClock.status = mementoAlarmClock.getStatus();
        alarmClock.type = mementoAlarmClock.getType();
        alarmClock.room = mementoAlarmClock.getRoom();
        alarmClock.time = mementoAlarmClock.time;
        alarmClock.frequency = mementoAlarmClock.frequency;
        alarmClock.song = mementoAlarmClock.song;
        return alarmClock;
    }

    class MementoAlarmClock extends MementoDevice {
        private final String time;
        private final String frequency;
        private final String song;

        public MementoAlarmClock(String id, String name, Boolean status, String type, Room room, String time, String frequency, String song) {
            super(id, name, status, type, room);
            this.time = time;
            this.frequency = frequency;
            this.song = song;
        }
    }
}
