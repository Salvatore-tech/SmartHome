package com.gruppo1.smarthome.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gruppo1.smarthome.memento.Memento;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "scene")
public class Scene extends SmartHomeItem implements Serializable {

    @Column(nullable = false)
    private Boolean status;
    private String period;
    @JsonIgnore
    @OneToMany(mappedBy = "scene", cascade = CascadeType.ALL)
    private List<Condition> conditions;

    public Scene() {
    }

    public Scene(String name, Boolean status, String period) {
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
        return new MementoScene(id, name, status, period, conditions);
    }

    @Override
    public SmartHomeItem restore(Memento memento) {
        Scene scene = new Scene();
        MementoScene mementoScene = (MementoScene) memento;
        scene.id = mementoScene.getId();
        scene.name = mementoScene.getName();
        scene.status = mementoScene.status;
        scene.period = mementoScene.period;
        scene.conditions = mementoScene.conditions;
        return scene;
    }

    class MementoScene extends Memento {
        private final Boolean status;
        private final String period;
        private final List<Condition> conditions;

        public MementoScene(String id, String name, Boolean status, String period, List<Condition> conditions) {
            this.id = id;
            this.name = name;
            this.status = status;
            this.period = period;
            this.conditions = conditions;
        }
    }
}
