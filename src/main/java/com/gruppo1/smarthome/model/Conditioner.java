package com.gruppo1.smarthome.model;

import com.gruppo1.smarthome.memento.Memento;
import com.gruppo1.smarthome.memento.MementoDevice;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.StringUtils;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "conditioner")
public class Conditioner extends Device {

    @ApiModelProperty(value = "15",position = 3)
    private Integer temperature;

    @ApiModelProperty(value = "Aria fredda",position = 4)
    private String settings;

    public Conditioner() {
        // do not remove
        super();
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
        return new MementoConditioner(id, label, name, status, type, room, temperature, settings);
    }

    @Override
    public SmartHomeItem restore(Memento memento) {
        MementoConditioner mementoConditioner = (MementoConditioner) memento;
        this.id = mementoConditioner.getId(); // TODO
        this.name = mementoConditioner.getName();
        this.status = mementoConditioner.getStatus();
        this.type = mementoConditioner.getType();
        this.room = mementoConditioner.getRoom();
        this.temperature = mementoConditioner.temperature;
        this.settings = mementoConditioner.settings;
        return this;
    }

    static class MementoConditioner extends MementoDevice {
        private final Integer temperature;
        private final String settings;

        public MementoConditioner(String id, String label, String name, Boolean status, String type, Room room, Integer temperature, String settings) {
            super(id, label, name, status, type, room);
            this.temperature = temperature;
            this.settings = settings;
        }
    }
}
