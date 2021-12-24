package com.gruppo1.smarthome.model;

import com.gruppo1.smarthome.memento.Memento;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;


@Entity
@Table(name = "profile")
public class Profile extends SmartHomeItem implements Serializable {

    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String surname;
    @Column(nullable = false)
    private String password;

    public Profile() {
    }

    public Profile(String email, String name, String surname, String password) {
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
        return new MementoProfile(id, name, surname, email, password);
    }

    @Override
    public SmartHomeItem restore(Memento memento) {
        Profile profile = new Profile();
        MementoProfile mementoProfile = (MementoProfile) memento;
        profile.id = mementoProfile.getId();
        profile.name = mementoProfile.getName();
        profile.surname = mementoProfile.surname;
        profile.email = mementoProfile.email;
        profile.password = mementoProfile.password;
        return profile;
    }

    static class MementoProfile extends Memento {
        private final String email;
        private final String surname;
        private final String password;

        public MementoProfile(String id, String name, String surname, String email, String password) {
            this.id = id;
            this.name = name;
            this.surname = surname;
            this.email = email;
            this.password = password;
        }
    }
}