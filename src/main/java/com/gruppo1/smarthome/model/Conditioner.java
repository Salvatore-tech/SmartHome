package com.gruppo1.smarthome.model;

import com.gruppo1.smarthome.memento.Memento;
import com.gruppo1.smarthome.memento.MementoDevice;
import org.apache.commons.lang.StringUtils;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "conditioner")
public class Conditioner extends Device {

    private Integer temperature;
    private String settings;

    public Conditioner() {
        // do not remove
    }

    public Conditioner(String type) {
        this.type = type;
    }

    public Conditioner(String name, String type, Boolean status, Integer temperature, String settings) {
        super(name, type, status);
        this.temperature = temperature;
        this.settings = settings;
    }

    public void setTemperature(Integer temperature) {
        this.temperature = temperature;
    }

    public void setSettings(String settings) {
        this.settings = settings;
    }

    public Integer getTemperature() {
        return temperature;
    }

    public String getSettings() {
        return settings;
    }

    @Override
    public String toString() {
        return "Conditioner {" +
                "name='" + name + '\'' +
                ", status=" + status +
                ", type='" + type + '\'' +
                ", room='" + (Objects.nonNull(room) ? room.getName() : StringUtils.EMPTY) + '\'' +
                ", temperature=" + temperature +
                ", settings='" + settings + '\'' +
                '}';
    }

    @Override
    public Memento createMemento() {
        return new MementoConditioner(id, name, status, type, room, temperature, settings);
    }

    @Override
    public SmartHomeItem restore(Memento memento) {
        Conditioner conditioner = new Conditioner();
        MementoConditioner mementoConditioner = (MementoConditioner) memento;
        conditioner.id = mementoConditioner.getId(); // TODO
        conditioner.name = mementoConditioner.getName();
        conditioner.status = mementoConditioner.getStatus();
        conditioner.type = mementoConditioner.getType();
        conditioner.room = mementoConditioner.getRoom();
        conditioner.temperature = mementoConditioner.temperature;
        conditioner.settings = mementoConditioner.settings;
        return conditioner;
    }

    class MementoConditioner extends MementoDevice {
        private Integer temperature;
        private String settings;

        public MementoConditioner(String id, String name, Boolean status, String type, Room room, Integer temperature, String settings) {
            super(id, name, status, type, room);
            this.temperature = temperature;
            this.settings = settings;
        }
    }
}
