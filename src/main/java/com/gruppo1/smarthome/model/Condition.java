package com.gruppo1.smarthome.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gruppo1.smarthome.command.api.Actions;
import com.gruppo1.smarthome.memento.Memento;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "condition")
public class Condition extends SmartHomeItem implements Serializable {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private Date activationDate;
    private Integer threshold;
    private String name;
    private Actions action;

    @ManyToOne
    @JoinColumn(name = "device_id")
    private Device device;

    @ManyToOne()
    @JoinColumn(name = "scene_id")
    private Scene scene;

    public Condition() {
        // do not remove
    }

    public Condition(String name, Actions action, Date activationDate, Integer threshold, Device device, Scene scene) {
        this.name = name;
        this.action = action;
        this.activationDate = activationDate;
        this.threshold = threshold;
        this.device = device;
        this.scene = scene;
    }

    public Date getActivationDate() {
        return activationDate;
    }

    public void setActivationDate(Date activation) {
        this.activationDate = activation;
    }

    public Integer getThreshold() {
        return threshold;
    }

    public void setThreshold(Integer threshold) {
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

    public Actions getAction() {
        return action;
    }

    public void setAction(Actions action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return "Condition{" +
                "threshold=" + threshold +
                ", name='" + name + '\'' +
                ", device=" + device.getName() +
                ", scene=" + scene.getName() +
                ", action=" + action +
                ", activation date=" + activationDate +
                '}';
    }

    @Override
    public Memento createMemento() {
        return new MementoCondition(id, name, scene, device, action, threshold, activationDate);
    }

    @Override
    public SmartHomeItem restore(Memento memento) {
        Condition condition = new Condition();
        MementoCondition mementoCondition = (MementoCondition) memento;
        condition.id = mementoCondition.getId(); // TODO
        condition.name = mementoCondition.getName(); // TODO
        condition.scene = mementoCondition.scene;
        condition.device = mementoCondition.device;
        condition.action = mementoCondition.action;
        condition.threshold = mementoCondition.threshold;
        condition.activationDate = mementoCondition.activationDate;
        return condition;
    }

    class MementoCondition extends Memento {
        private final Scene scene;
        private final Device device;
        private final Actions action;
        private final Integer threshold;
        private final Date activationDate;

        public MementoCondition(String id, String name, Scene scene, Device device, Actions action, Integer threshold, Date activationDate) {
            this.id = id;
            this.name = name;
            this.scene = scene;
            this.device = device;
            this.action = action;
            this.threshold = threshold;
            this.activationDate = activationDate;
        }
    }
}
