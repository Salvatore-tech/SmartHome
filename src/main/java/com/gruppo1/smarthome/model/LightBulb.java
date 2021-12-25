package com.gruppo1.smarthome.model;

import com.gruppo1.smarthome.memento.Memento;
import com.gruppo1.smarthome.memento.MementoDevice;
import org.apache.commons.lang.StringUtils;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "lightbulb")
public class LightBulb extends Device {

    private Integer brightness;
    private String colorTemperature;

    public LightBulb() {
        // do not remove
        super();
    }

    public LightBulb(String type) {
        this.type = type;
    }

    public LightBulb(String name, String type, Boolean status, Integer brightness, String colorTemperature) {
        super(name, type, status);
        this.brightness = brightness;
        this.colorTemperature = colorTemperature;
    }

    public void setBrightness(Integer brightness) {
        this.brightness = brightness;
    }

    public void setColorTemperature(String colorTemp) {
        this.colorTemperature = colorTemp;
    }

    public Integer getBrightness() {
        return brightness;
    }

    public String getColorTemperature() {
        return colorTemperature;
    }

    @Override
    public String toString() {
        return "LightBulb {" +
                "name='" + name + '\'' +
                ", status=" + status +
                ", type='" + type + '\'' +
                ", room='" + (Objects.nonNull(room) ? room.getName() : StringUtils.EMPTY) + '\'' +
                ", brightness=" + brightness +
                ", colorTemp=" + colorTemperature +
                '}';
    }

    @Override
    public MementoLightBulb createMemento() {
        return new MementoLightBulb(id, label, name, status, type, room, brightness, colorTemperature);
    }

    @Override
    public SmartHomeItem restore(Memento memento) {
        MementoLightBulb mementoLightBulb = (MementoLightBulb) memento;
        this.id = mementoLightBulb.getId();
        this.name = mementoLightBulb.getName();
        this.status = mementoLightBulb.getStatus();
        this.type = mementoLightBulb.getType();
        this.room = mementoLightBulb.getRoom();
        this.brightness = mementoLightBulb.brightness;
        this.colorTemperature = mementoLightBulb.colorTemperature;
        return this;
    }

    static class MementoLightBulb extends MementoDevice {
        private final Integer brightness;
        private final String colorTemperature;

        public MementoLightBulb(String id, String label, String name, Boolean status, String type, Room room, Integer brightness, String colorTemperature) {
            super(id, label, name, status, type, room);
            this.brightness = brightness;
            this.colorTemperature = colorTemperature;
        }
    }
}
