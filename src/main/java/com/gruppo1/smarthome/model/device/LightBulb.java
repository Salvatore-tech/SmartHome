package com.gruppo1.smarthome.model.device;

import org.apache.commons.lang.StringUtils;

import javax.persistence.Entity;
import java.util.Objects;

@Entity
public class LightBulb extends Device {

    private Integer brightness;
    private String colorTemp;

    public LightBulb() {
        super(StringUtils.EMPTY);
    }

    public LightBulb(String type) { this.type = type; }

    public LightBulb(String name, String type, Integer brightness, String colorTemp) {
        super(name, type);
        this.brightness = brightness;
        this.colorTemp = colorTemp;
    }

    public void setBrightness(Integer brightness) {
        this.brightness = brightness;
    }

    public void setColorTemp(String colorTemp) {
        this.colorTemp = colorTemp;
    }

    public Integer getBrightness() {
        return brightness;
    }

    public String getColorTemp() {
        return colorTemp;
    }

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

    @Override
    public MementoLightBulb createMemento() {
        return new MementoLightBulb();
    }

    class MementoLightBulb extends MementoDevice {
        private Integer memBrightness;
        private String memColorTemp;
        private String memName;

        public MementoLightBulb() {
            super();
            this.memBrightness = brightness;
            this.memColorTemp = colorTemp;
            this.memName = name;
        }
    }
}
