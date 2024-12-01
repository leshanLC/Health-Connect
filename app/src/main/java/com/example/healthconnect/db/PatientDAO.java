package com.example.healthconnect.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;
import com.example.healthconnect.datamodel.*;

public class PatientDAO {
    private SQLiteDatabase db;

    public PatientDAO(Context context) {
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long addPatient(Patient patient) {
        ContentValues values = new ContentValues();
        values.put("phn", patient.getPhn());
        values.put("name", patient.getName());
        values.put("birthday", patient.getBirthday());
        values.put("gender", patient.getGender());
        values.put("address", patient.getAddress());
        values.put("phone", patient.getPhone());
        values.put("practitioner_id", patient.getPractitionerId());

        return db.insert("PATIENT", null, values);
    }

    public List<Patient> getAllPatients() {
        List<Patient> patients = new ArrayList<>();
        Cursor cursor = db.query("PATIENT", null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Patient patient = new Patient();
                try{
                    patient.setPhn(cursor.getInt(cursor.getColumnIndexOrThrow("phn")));
                    patient.setPhn(cursor.getInt(cursor.getColumnIndexOrThrow("phn")));
                    patient.setName(cursor.getString(cursor.getColumnIndexOrThrow("name")));
                    patient.setBirthday(cursor.getString(cursor.getColumnIndexOrThrow("birthday")));
                    patient.setGender(cursor.getString(cursor.getColumnIndexOrThrow("gender")));
                    patient.setAddress(cursor.getString(cursor.getColumnIndexOrThrow("address")));
                    patient.setPhone(cursor.getString(cursor.getColumnIndexOrThrow("phone")));
                    patient.setPractitionerId(cursor.getInt(cursor.getColumnIndexOrThrow("practitioner_id")));
                } catch(Exception e){
                    System.out.println(e);
                }
                patients.add(patient);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return patients;
    }

    public Patient getPatientByPhn(int phn) {
        Cursor cursor = db.query("PATIENT", null, "phn = ?", new String[]{String.valueOf(phn)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            Patient patient = new Patient();
            try{
                patient.setPhn(cursor.getInt(cursor.getColumnIndexOrThrow("phn")));
                patient.setName(cursor.getString(cursor.getColumnIndexOrThrow("name")));
                patient.setBirthday(cursor.getString(cursor.getColumnIndexOrThrow("birthday")));
                patient.setGender(cursor.getString(cursor.getColumnIndexOrThrow("gender")));
                patient.setAddress(cursor.getString(cursor.getColumnIndexOrThrow("address")));
                patient.setPhone(cursor.getString(cursor.getColumnIndexOrThrow("phone")));
                patient.setPractitionerId(cursor.getInt(cursor.getColumnIndexOrThrow("practitioner_id")));
            } catch(Exception e){
                System.out.println(e);
            }
            cursor.close();
            return patient;
        }

        if (cursor != null) {
            cursor.close();
        }
        return null;
    }

    public int updatePatient(Patient patient) {
        ContentValues values = new ContentValues();
        values.put("name", patient.getName());
        values.put("birthday", patient.getBirthday());
        values.put("gender", patient.getGender());
        values.put("address", patient.getAddress());
        values.put("phone", patient.getPhone());
        values.put("practitioner_id", patient.getPractitionerId());

        return db.update("PATIENT", values, "phn = ?", new String[]{String.valueOf(patient.getPhn())});
    }

    public int deletePatient(int phn) {
        return db.delete("PATIENT", "phn = ?", new String[]{String.valueOf(phn)});
    }

    public List<Appointment> getAppointmentsByPractitionerId(int practitionerId) {
        List<Appointment> appointments = new ArrayList<>();

        // Query to join PATIENT and APPOINTMENT tables
        String query = "SELECT A.id, A.patient_phn, A.date_time, A.reason " +
                "FROM APPOINTMENT A " +
                "INNER JOIN PATIENT P ON A.patient_phn = P.phn " +
                "WHERE P.practitioner_id = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(practitionerId)});

        // Process the results
        if (cursor.moveToFirst()) {
            do {
                Appointment appointment = new Appointment();
                appointment.setId(cursor.getInt(cursor.getColumnIndex("id")));
                appointment.setPatientPhn(cursor.getInt(cursor.getColumnIndex("patient_phn")));
                appointment.setDateTime(cursor.getString(cursor.getColumnIndex("date_time")));
                appointment.setReason(cursor.getString(cursor.getColumnIndex("reason")));
                appointments.add(appointment);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return appointments;
    }

}
