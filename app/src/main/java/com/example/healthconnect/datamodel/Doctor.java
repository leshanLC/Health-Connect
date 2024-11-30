package com.example.healthconnect.datamodel;

import java.io.Serializable;

public class Doctor implements Serializable {
    private int practitionerId;
    private String name;
    private String phone;
    private String email;
    private String password;
    private String gender;

    public Doctor() {
    }

    public Doctor(int practitionerId, String name, String phone, String email, String password, String gender) {
        this.practitionerId = practitionerId;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.gender = gender;
    }

    public int getPractitionerId() {
        return practitionerId;
    }

    public void setPractitionerId(int practitionerId) {
        this.practitionerId = practitionerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
