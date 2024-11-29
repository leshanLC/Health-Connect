package com.example.healthconnect.datamodel;

public class Appointment {
    private int id;
    private int patientPhn;
    private String dateTime;
    private String reason;

    public Appointment() {
    }

    public Appointment(int id, int patientPhn, String dateTime, String reason) {
        this.id = id;
        this.patientPhn = patientPhn;
        this.dateTime = dateTime;
        this.reason = reason;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPatientPhn() {
        return patientPhn;
    }

    public void setPatientPhn(int patientPhn) {
        this.patientPhn = patientPhn;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
