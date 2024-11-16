package com.example.healthconnect.datamodel;

public class HistoryMedication {
    private int medId;
    private int historyId;
    private String medicineName;
    private String form;
    private String strength;
    private String dosage;
    private String duration;

    public HistoryMedication() {
    }

    public HistoryMedication(int medId, int historyId, String medicineName, String form, String strength, String dosage, String duration) {
        this.medId = medId;
        this.historyId = historyId;
        this.medicineName = medicineName;
        this.form = form;
        this.strength = strength;
        this.dosage = dosage;
        this.duration = duration;
    }

    public int getMedId() {
        return medId;
    }

    public void setMedId(int medId) {
        this.medId = medId;
    }

    public int getHistoryId() {
        return historyId;
    }

    public void setHistoryId(int historyId) {
        this.historyId = historyId;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public String getStrength() {
        return strength;
    }

    public void setStrength(String strength) {
        this.strength = strength;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
