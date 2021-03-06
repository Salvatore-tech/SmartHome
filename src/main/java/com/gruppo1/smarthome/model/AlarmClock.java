package com.gruppo1.smarthome.model;

import com.gruppo1.smarthome.memento.Memento;
import com.gruppo1.smarthome.memento.MementoDevice;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.StringUtils;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "alarmclock")
public class AlarmClock extends Device {

    @ApiModelProperty(example = "7:00 AM", value = "Current time", position = 5)
    private String time;

    @ApiModelProperty(example = "Rain drops", value = "Song of the alarmclock", position = 7)
    private String song;

    public AlarmClock() {
        // do not remove
        super();
    }

    public AlarmClock(String type) {
        this.type = type;
    }

    public AlarmClock(String name, String type, Boolean status, String time, String song) {
        super(name, type, status);
        this.time = time;
        this.song = song;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
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
                ", song='" + song + '\'' +
                '}';
    }

    @Override
    public Memento createMemento() {
        return new MementoAlarmClock(id, label, name, status, type, room, time, song);
    }

    @Override
    public SmartHomeItem restore(Memento memento) {
        MementoAlarmClock mementoAlarmClock = (MementoAlarmClock) memento;
        this.id = mementoAlarmClock.getId();
        this.name = mementoAlarmClock.getName();
        this.status = mementoAlarmClock.getStatus();
        this.type = mementoAlarmClock.getType();
        this.room = mementoAlarmClock.getRoom();
        this.time = mementoAlarmClock.time;
        this.song = mementoAlarmClock.song;
        return this;
    }

    static class MementoAlarmClock extends MementoDevice {
        private final String time;
        private final String song;

        public MementoAlarmClock(String id, String label, String name, Boolean status, String type, Room room, String time, String song) {
            super(id, label, name, status, type, room);
            this.time = time;
            this.song = song;
        }
    }
}
