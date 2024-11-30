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
                patient.setPhn(cursor.getInt(cursor.getColumnIndex("phn")));
                patient.setName(cursor.getString(cursor.getColumnIndex("name")));
                patient.setBirthday(cursor.getString(cursor.getColumnIndex("birthday")));
                patient.setGender(cursor.getString(cursor.getColumnIndex("gender")));
                patient.setAddress(cursor.getString(cursor.getColumnIndex("address")));
                patient.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
                patient.setPractitionerId(cursor.getInt(cursor.getColumnIndex("practitioner_id")));
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
            patient.setPhn(cursor.getInt(cursor.getColumnIndex("phn")));
            patient.setName(cursor.getString(cursor.getColumnIndex("name")));
            patient.setBirthday(cursor.getString(cursor.getColumnIndex("birthday")));
            patient.setGender(cursor.getString(cursor.getColumnIndex("gender")));
            patient.setAddress(cursor.getString(cursor.getColumnIndex("address")));
            patient.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
            patient.setPractitionerId(cursor.getInt(cursor.getColumnIndex("practitioner_id")));
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

    public List<Patient> getPatientsByPractitionerId(int practitionerId) {
        List<Patient> patients = new ArrayList<>();

        // Query the database
        Cursor cursor = db.query(
                "PATIENT",
                null,
                "practitioner_id = ?",
                new String[]{String.valueOf(practitionerId)},
                null,
                null,
                null
        );

        // Process the results
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Patient patient = new Patient();
                patient.setPhn(cursor.getInt(cursor.getColumnIndex("phn")));
                patient.setName(cursor.getString(cursor.getColumnIndex("name")));
                patient.setBirthday(cursor.getString(cursor.getColumnIndex("birthday")));
                patient.setGender(cursor.getString(cursor.getColumnIndex("gender")));
                patient.setAddress(cursor.getString(cursor.getColumnIndex("address")));
                patient.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
                patient.setPractitionerId(cursor.getInt(cursor.getColumnIndex("practitioner_id")));
                patients.add(patient);
            } while (cursor.moveToNext());

            cursor.close();
        }

        return patients;
    }

}
