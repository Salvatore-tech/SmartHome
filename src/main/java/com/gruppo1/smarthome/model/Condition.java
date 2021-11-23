package com.gruppo1.smarthome.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Condition extends SmartHomeItem implements Serializable {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(nullable = false, updatable = false)
    private String id;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private Date activationDate;
    private String period;
    private Double threshold;
    private String name;

    @ManyToOne()
    @JoinColumn(name = "device_id")
    private Device device;

    @ManyToOne()
    @JoinColumn(name = "scene_id")
    private Scene scene;

    public Condition() {
    }

    public Condition(String name, Device device, Scene scene, Double threshold) {
        this.name = name;
        this.device = device;
        this.scene = scene;
        this.threshold = threshold;
    }

    public Date getActivationDate() {
        return activationDate;
    }

    public void setActivationDate(Date activation) { this.activationDate = activation; }

    public Double getThreshold() {
        return threshold;
    }

    public void setThreshold(Double threshold) {
        this.threshold = threshold;
    }

    public String getName() { return this.name; }

    public void setName(String name) { this.name = name; }

    public Device getDevice() {
        return this.device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public Scene getScene() {
        return this.scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    @Override
    public String toString() {
        return "Condition{" +
                "threshold=" + threshold +
                ", name='" + name + '\'' +
                ", device=" + device.getName() +
                ", scene=" + scene.getName() +
                '}';
    }
}
