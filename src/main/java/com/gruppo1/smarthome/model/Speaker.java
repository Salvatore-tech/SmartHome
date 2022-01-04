package com.gruppo1.smarthome.model;

import com.gruppo1.smarthome.memento.Memento;
import com.gruppo1.smarthome.memento.MementoDevice;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.StringUtils;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "speaker")
public class Speaker extends Device {

    @ApiModelProperty(value = "100",position = 20)
    private Integer power;

    @ApiModelProperty(value = "Bose",position = 21)
    private String brand;

    @ApiModelProperty(value = "SCS-2021",position = 22)
    private String model;

    public Speaker() {
        // do not remove
        super();
    }

    public Speaker(String type) {
        super();
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
        return new MementoSpeaker(id, label, name, status, type, room, power, brand, model);
    }

    @Override
    public SmartHomeItem restore(Memento memento) {
        MementoSpeaker mementoSpeaker = (MementoSpeaker) memento;
        this.id = mementoSpeaker.getId();
        this.name = mementoSpeaker.getName();
        this.status = mementoSpeaker.getStatus();
        this.type = mementoSpeaker.getType();
        this.room = mementoSpeaker.getRoom();
        this.power = mementoSpeaker.power;
        this.brand = mementoSpeaker.brand;
        this.model = mementoSpeaker.model;
        return this;
    }

    static class MementoSpeaker extends MementoDevice {
        private final Integer power;
        private final String brand;
        private final String model;

        public MementoSpeaker(String id, String label, String name, Boolean status, String type, Room room, Integer power, String brand, String model) {
            super(id, label, name, status, type, room);
            this.power = power;
            this.brand = brand;
            this.model = model;
        }
    }
}
