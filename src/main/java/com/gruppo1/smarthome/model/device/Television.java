package com.gruppo1.smarthome.model.device;

import com.gruppo1.smarthome.model.Device;
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
        super(StringUtils.EMPTY);
    }

    public Television(String name, String type, String brand, String model, Integer volume, Integer channel) {
        super(name, type);
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
}
