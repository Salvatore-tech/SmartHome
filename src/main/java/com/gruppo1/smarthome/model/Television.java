package com.gruppo1.smarthome.model;

import com.gruppo1.smarthome.memento.Memento;
import com.gruppo1.smarthome.memento.MementoDevice;
import org.apache.commons.lang.StringUtils;

import javax.persistence.Entity;
import java.util.Objects;

@Entity
public class Television extends Device {

    private String brand;
    private String model;
    private Integer volume;
    private Integer channel;

    public Television() {
        // do not remove
    }

    public Television(String type) {
        this.type = type;
    }

    public Television(String name, String type, Boolean status, String brand, String model, Integer volume, Integer channel) {
        super(name, type, status);
        this.brand = brand;
        this.model = model;
        this.volume = volume;
        this.channel = channel;
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

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }

    public Integer getVolume() {
        return volume;
    }

    public Integer getChannel() {
        return channel;
    }

    @Override
    public String toString() {
        return "Television {" +
                "name='" + name + '\'' +
                ", status=" + status +
                ", type='" + type + '\'' +
                ", room=" + (Objects.nonNull(room) ? room.getName() : StringUtils.EMPTY) + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", volume=" + volume +
                ", channel=" + channel +
                '}';
    }

    @Override
    public Memento createMemento() {
        return new MementoTelevision(id, name, status, type, room, brand, model, volume, channel);
    }

    @Override
    public SmartHomeItem restore(Memento memento) {
        Television television = new Television();
        MementoTelevision mementoTelevision = (MementoTelevision) memento;
        television.id = mementoTelevision.getId();
        television.name = mementoTelevision.getName();
        television.status = mementoTelevision.getStatus();
        television.type = mementoTelevision.getType();
        television.room = mementoTelevision.getRoom();
        television.brand = mementoTelevision.brand;
        television.model = mementoTelevision.model;
        television.volume = mementoTelevision.volume;
        television.channel = mementoTelevision.channel;
        return television;
    }

    class MementoTelevision extends MementoDevice {
        private final String brand;
        private final String model;
        private final Integer volume;
        private final Integer channel;

        public MementoTelevision(String id, String name, Boolean status, String type, Room room, String brand, String model, Integer volume, Integer channel) {
            super(id, name, status, type, room);
            this.brand = brand;
            this.model = model;
            this.volume = volume;
            this.channel = channel;
        }
    }
}
