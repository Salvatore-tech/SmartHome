package com.gruppo1.smarthome.model.device;

import com.gruppo1.smarthome.memento.Memento;
import com.gruppo1.smarthome.model.Device;
import org.apache.commons.lang.StringUtils;

import javax.persistence.Entity;
import java.util.Objects;

@Entity
public class AlarmClock extends Device {

    private String time;
    private String frequency;
    private String song;

    public AlarmClock() {
        super(StringUtils.EMPTY);
    }

    public AlarmClock(String type) { this.type = type; }

    public AlarmClock(String name, String type, String time, String frequency, String song) {
        super(name, type);
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
        return new MementoAlarmClock();
    }

    class MementoAlarmClock extends Device.MementoDevice {
        private String memTime;
        private String memFrequency;
        private String memSong;

        public MementoAlarmClock() {
            super();
            this.memTime = time;
            this.memFrequency = frequency;
            this.memSong = song;
        }


    }
}
