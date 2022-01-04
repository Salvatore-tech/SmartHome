package com.gruppo1.smarthome.model;

import com.gruppo1.smarthome.memento.Memento;
import com.gruppo1.smarthome.memento.MementoDevice;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.StringUtils;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "television")
public class Television extends Device {

    @ApiModelProperty(value = "Samsung",position = 23)
    private String brand;

    @ApiModelProperty(value = "TGKSLS09",position = 24)
    private String model;

    @ApiModelProperty(value = "15",position = 25)
    private Integer volume;

    @ApiModelProperty(value = "0",position = 26)
    private Integer channel;

    public Television() {
        // do not remove
    }

    public Television(String type) {
        super();
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
        return new MementoTelevision(id, label, name, status, type, room, brand, model, volume, channel);
    }

    @Override
    public SmartHomeItem restore(Memento memento) {
        MementoTelevision mementoTelevision = (MementoTelevision) memento;
        this.id = mementoTelevision.getId();
        this.name = mementoTelevision.getName();
        this.status = mementoTelevision.getStatus();
        this.type = mementoTelevision.getType();
        this.room = mementoTelevision.getRoom();
        this.brand = mementoTelevision.brand;
        this.model = mementoTelevision.model;
        this.volume = mementoTelevision.volume;
        this.channel = mementoTelevision.channel;
        return this;
    }

    static class MementoTelevision extends MementoDevice {
        private final String brand;
        private final String model;
        private final Integer volume;
        private final Integer channel;

        public MementoTelevision(String id, String label, String name, Boolean status, String type, Room room, String brand, String model, Integer volume, Integer channel) {
            super(id, label, name, status, type, room);
            this.brand = brand;
            this.model = model;
            this.volume = volume;
            this.channel = channel;
        }
    }
}
