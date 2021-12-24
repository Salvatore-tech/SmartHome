package com.gruppo1.smarthome.model;

import com.gruppo1.smarthome.memento.Memento;
import com.gruppo1.smarthome.memento.MementoDevice;
import org.apache.commons.lang.StringUtils;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "speaker")
public class Speaker extends Device {

    private Integer power;
    private String brand;
    private String model;

    public Speaker() {
        // do not remove
    }

    public Speaker(String type) {
        this.type = type;
    }

    public Speaker(String name, String type, Boolean status, Integer power, String brand, String model) {
        super(name, type, status);
        this.power = power;
        this.brand = brand;
        this.model = model;
    }

    public void setPower(Integer volume) {
        this.power = volume;
    }

    public Integer getPower() {
        return power;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Override
    public String toString() {
        return "Speaker{" +
                "name='" + name + '\'' +
                ", status=" + status +
                ", type='" + type + '\'' +
                ", room='" + (Objects.nonNull(room) ? room.getName() : StringUtils.EMPTY) + '\'' +
                ", volume=" + power +
                '}';
    }

    @Override
    public Memento createMemento() {
        return new MementoSpeaker(id, name, status, type, room, power, brand, model);
    }

    @Override
    public SmartHomeItem restore(Memento memento) {
        MementoSpeaker mementoSpeaker = (MementoSpeaker) memento;
        Speaker speaker = new Speaker();
        speaker.id = mementoSpeaker.getId();
        speaker.name = mementoSpeaker.getName();
        speaker.status = mementoSpeaker.getStatus();
        speaker.type = mementoSpeaker.getType();
        speaker.room = mementoSpeaker.getRoom();
        speaker.power = mementoSpeaker.power;
        speaker.brand = mementoSpeaker.brand;
        speaker.model = mementoSpeaker.model;
        return speaker;
    }

    static class MementoSpeaker extends MementoDevice {
        private final Integer power;
        private final String brand;
        private final String model;

        public MementoSpeaker(String id, String name, Boolean status, String type, Room room, Integer power, String brand, String model) {
            super(id, name, status, type, room);
            this.power = power;
            this.brand = brand;
            this.model = model;
        }
    }
}
