package com.gruppo1.smarthome.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang.StringUtils;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Speaker extends Device{
    @JsonIgnore
    @Id
    @Column(nullable = false, updatable = false)
    private String id;
    private Integer power;
    private String brand;
    private String model;

    public Speaker() {
        super(StringUtils.EMPTY);
    }

    public Speaker(String name, String type, Integer power, String brand, String model) {
        super(name, type);
        this.power = power;
        this.brand = brand;
        this.model = model;
    }

    public void setPower(Integer volume) {this.power = volume;}

    public Integer getPower(){return power;}

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
}
