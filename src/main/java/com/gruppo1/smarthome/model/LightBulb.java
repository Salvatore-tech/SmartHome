package com.gruppo1.smarthome.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang.StringUtils;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class LightBulb extends Device{
    @JsonIgnore
    @Id
    @Column(nullable = false, updatable = false)
    private String id;
    private Integer brightness;
    private String colorTemp;

    public LightBulb() {
        super(StringUtils.EMPTY);
    }

    public LightBulb(String name, String type, Integer brightness, String colorTemp) {
        super(name, type);
        this.brightness = brightness;
        this.colorTemp = colorTemp;
    }

    public void setBrightness(Integer brightness) {
        this.brightness = brightness;
    }

    public void setColorTemp(String colorTemp){
        this.colorTemp = colorTemp;
    }

    public Integer getBrightness(){return brightness;}

    public String getColorTemp(){return colorTemp;}

    @Override
    public String toString() {
        return "LightBulb {" +
                "name='" + name + '\'' +
                ", status=" + status +
                ", type='" + type + '\'' +
                ", room='" + (Objects.nonNull(room) ? room.getName() : StringUtils.EMPTY) + '\'' +
                ", brightness=" + brightness +
                ", colorTemp=" + colorTemp +
                '}';
    }
}
