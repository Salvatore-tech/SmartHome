package com.gruppo1.smarthome.model;

import com.gruppo1.smarthome.memento.Memento;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;


@Entity
@Table(name = "profile")
public class Profile extends SmartHomeItem implements Serializable {

    @Column(nullable = false)
    @ApiModelProperty(value = "harry@gmail.com",position = 15)
    private String email;

    @Column(nullable = false)
    @ApiModelProperty(value = "Jacob",position = 16)
    private String surname;

    @Column(nullable = false)
    @ApiModelProperty(value = "abc",position = 17)
    private String password;

    public Profile() {
        this.label = "Profile";
    }

    public Profile(String email, String name, String surname, String password) {
        this.label = "Profile";
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }

    @Override
    public Memento createMemento() {
        return new MementoProfile(id, label, name, surname, email, password);
    }

    @Override
    public SmartHomeItem restore(Memento memento) {
        MementoProfile mementoProfile = (MementoProfile) memento;
        this.id = mementoProfile.getId();
        this.name = mementoProfile.getName();
        this.surname = mementoProfile.surname;
        this.email = mementoProfile.email;
        this.password = mementoProfile.password;
        return this;
    }

    static class MementoProfile extends Memento {
        private final String email;
        private final String surname;
        private final String password;

        public MementoProfile(String id, String label, String name, String surname, String email, String password) {
            this.id = id;
            this.label = label;
            this.name = name;
            this.surname = surname;
            this.email = email;
            this.password = password;
        }
    }
}