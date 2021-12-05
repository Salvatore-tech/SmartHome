package com.gruppo1.smarthome.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gruppo1.smarthome.memento.Memento;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Scene extends SmartHomeItem implements Serializable {

    @JsonIgnore
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(nullable = false, updatable = false)
    private String id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private Boolean status;

    @JsonIgnore
    @OneToMany(mappedBy = "scene", cascade = CascadeType.ALL)
    private List<Condition> conditions;

    public Scene() {}

    public Scene(String name) {
        this.name = name;
        this.status = false;
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

    @Override
    public Memento createMemento() {
        return new MementoScene();
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
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
                ", status=" + status +
                '}';
    }

    class MementoScene extends Memento {
        private String memId;
        private String memName;
        private Boolean memStatus;
        private List<Condition> memConditions;


        public MementoScene() {
            this.memId = id;
            this.memName = name;
            this.memStatus = status;
            this.memConditions = conditions;
        }

        @Override
        public String getName() {
            return memName;
        }
    }
}
