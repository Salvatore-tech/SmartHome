package com.gruppo1.smarthome.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gruppo1.smarthome.memento.Memento;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "scene")
public class Scene extends SmartHomeItem implements Serializable {

    @Column(nullable = false)
    @ApiModelProperty(example = "true", value = "Status of scene", position = 18)
    private Boolean status;

    @ApiModelProperty(example = "Daily", value = "Period of activation", position = 19)
    private String period;

    @JsonIgnore
    @OneToMany(mappedBy = "scene", cascade = CascadeType.ALL)
    private List<Condition> conditions;

    public Scene() {
        this.label = "Scene";
    }

    public Scene(String name, Boolean status, String period) {
        this.label = "Scene";
        this.name = name;
        this.status = status;
        this.period = period;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public List<Condition> getConditions() {
        return this.conditions;
    }

    public void addCondition(Condition condition) {
        this.conditions.add(condition);
    }

    public void removeCondition(Condition condition) {
        this.conditions.remove(condition);
    }

    @Override
    public String toString() {
        return "Scene{" +
                "name='" + name + '\'' +
                ", period='" + period + '\'' +
                ", status=" + status +
                '}';
    }

    @Override
    public Memento createMemento() {
        return new MementoScene(id, label, name, status, period);
    }

    @Override
    public SmartHomeItem restore(Memento memento) {
        MementoScene mementoScene = (MementoScene) memento;
        this.id = mementoScene.getId();
        this.name = mementoScene.getName();
        this.status = mementoScene.status;
        this.period = mementoScene.period;
        return this;
    }

    static class MementoScene extends Memento {
        private final Boolean status;
        private final String period;

        public MementoScene(String id, String label, String name, Boolean status, String period) {
            this.id = id;
            this.label = label;
            this.name = name;
            this.status = status;
            this.period = period;
        }
    }
}
