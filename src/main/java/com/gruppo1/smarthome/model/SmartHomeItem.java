package com.gruppo1.smarthome.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gruppo1.smarthome.memento.Memento;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
public abstract class SmartHomeItem implements Serializable {

    @JsonIgnore
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(nullable = false, updatable = false)
    protected String id;

    @ApiModelProperty(value = "The name of the item",position = 1)
    @Column
    protected String name;

    @JsonIgnore
    @ApiModelProperty(value = "The name of the item",position = 2)
    protected String label;

    protected SmartHomeItem() {
        this.label = "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() { return label; }

    public abstract Memento createMemento();

    public abstract SmartHomeItem restore(Memento memento);
}
