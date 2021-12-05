package com.gruppo1.smarthome.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gruppo1.smarthome.memento.Memento;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;


@Entity
public class Profile extends SmartHomeItem implements Serializable {

    @JsonIgnore
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(nullable = false, updatable = false)
    private String id;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String surname;
    @Column(nullable = false)
    private String password;

    public Profile() {}

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

    @Override
    public Memento createMemento() {
        return null;
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

    class MementoProfile extends Memento {
        private String memId;
        private String memEmail;
        private String memName;
        private String memSurname;
        private String memPassword;


        public MementoProfile() {
            this.memId = id;
            this.memEmail = email;
            this.memName = name;
            this.memSurname = surname;
            this.memPassword = password;
        }

        @Override
        public String getName() {
            return memName;
        }

        @Override
        public void setName(String name) {

        }
    }


}