package com.example.healthconnect.datamodel;

import java.util.ArrayList;
import java.util.List;

public class PatientHistory {
    private int historyId;
    private int patientPhn;
    private String dateTime;
    private double weight;
    private double height;
    private String diagnoses;
    private String treatments;

    public PatientHistory() {
    }

    public PatientHistory(int historyId, int patientPhn, String dateTime, double weight, double height, String diagnoses, String treatments) {
        this.historyId = historyId;
        this.patientPhn = patientPhn;
        this.dateTime = dateTime;
        this.weight = weight;
        this.height = height;
        this.diagnoses = diagnoses;
        this.treatments = treatments;
    }

    public PatientHistory(String dateTime, double weight, double height, String diagnoses, String treatments) {
        this.dateTime = dateTime;
        this.weight = weight;
        this.height = height;
        this.diagnoses = diagnoses;
        this.treatments = treatments;
    }

    public int getHistoryId() {
        return historyId;
    }

    public void setHistoryId(int historyId) {
        this.historyId = historyId;
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

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getDiagnoses() {
        return diagnoses;
    }

    public void setDiagnoses(String diagnoses) {
        this.diagnoses = diagnoses;
    }

    public String getTreatments() {
        return treatments;
    }

    public void setTreatments(String treatments) {
        this.treatments = treatments;
    }
}
