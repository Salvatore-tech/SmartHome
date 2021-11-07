package com.gruppo1.smarthome.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Conditions extends SmartHomeItem implements Serializable {

    @EmbeddedId
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(nullable = false, updatable = false)
    private ConditionId conditionId = new ConditionId();
    private Date activationDate;
    private String period;
    private Double threshold;
    private String name;

    @ManyToOne
    @MapsId("deviceId")
    @JoinColumn(name = "device_id")
    private Device device;

    @ManyToOne
    @MapsId("sceneId")
    @JoinColumn(name = "scene_id")
    private Scene scene;


    public Conditions() {
    }

    public Conditions(ConditionId conditionId, Date activationDate, Double threshold) {
        this.conditionId = conditionId;
        this.activationDate = activationDate;
        this.threshold = threshold;
    }

    public Conditions(Device device, Scene scene) {
        this.device = device;
        this.scene = scene;
    }

    public ConditionId getConditionId() {
        return conditionId;
    }

    public void setConditionId(ConditionId conditionId) {
        this.conditionId = conditionId;
    }

    public Date getActivation() {
        return activationDate;
    }

    public void setActivation(Date activation) {
        this.activationDate = activation;
    }

    public Double getThreshold() {
        return threshold;
    }

    public void setThreshold(Double threshold) {
        this.threshold = threshold;
    }

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

}
