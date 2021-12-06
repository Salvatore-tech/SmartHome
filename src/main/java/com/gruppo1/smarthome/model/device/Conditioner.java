package com.gruppo1.smarthome.model.device;

import com.gruppo1.smarthome.memento.Memento;
import org.apache.commons.lang.StringUtils;

import javax.persistence.Entity;
import java.util.Objects;

@Entity
public class Conditioner extends Device {

    private Integer temperature;
    private String settings;

    public Conditioner() {
        super(StringUtils.EMPTY);
    }

    public Conditioner(String type) {
        this.type = type;
    }

    public Conditioner(String name, String type, Integer temperature, String settings) {
        super(name, type);
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
        return new MementoConditioner();
    }


    class MementoConditioner extends Device.MementoDevice {
        private Integer memTemperature;
        private String memSettings;

        public MementoConditioner() {
            super();
            this.memTemperature = temperature;
            this.memSettings = settings;
        }

    }
}
