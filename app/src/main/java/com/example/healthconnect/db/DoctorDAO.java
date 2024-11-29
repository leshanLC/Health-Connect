package com.example.healthconnect.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;
import com.example.healthconnect.datamodel.*;

public class DoctorDAO {
    private SQLiteDatabase db;

    public DoctorDAO(Context context) {
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long addDoctor(Doctor doctor) {
        ContentValues values = new ContentValues();
        values.put("practitioner_id", doctor.getPractitionerId());
        values.put("name", doctor.getName());
        values.put("phone", doctor.getPhone());
        values.put("email", doctor.getEmail());
        values.put("password", doctor.getPassword());
        values.put("gender", doctor.getGender());

        return db.insert("DOCTOR", null, values);
    }

    public List<Doctor> getAllDoctors() {
        List<Doctor> doctors = new ArrayList<>();
        Cursor cursor = db.query("DOCTOR", null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Doctor doctor = new Doctor();
                doctor.setPractitionerId(cursor.getInt(cursor.getColumnIndex("practitioner_id")));
                doctor.setName(cursor.getString(cursor.getColumnIndex("name")));
                doctor.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
                doctor.setEmail(cursor.getString(cursor.getColumnIndex("email")));
                doctor.setPassword(cursor.getString(cursor.getColumnIndex("password")));
                doctor.setGender(cursor.getString(cursor.getColumnIndex("gender")));
                doctors.add(doctor);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return doctors;
    }

    public Doctor getDoctorById(int practitionerId) {
        Cursor cursor = db.query(
                "DOCTOR",
                null,
                "practitioner_id = ?",
                new String[]{String.valueOf(practitionerId)},
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            Doctor doctor = new Doctor();
            doctor.setPractitionerId(cursor.getInt(cursor.getColumnIndex("practitioner_id")));
            doctor.setName(cursor.getString(cursor.getColumnIndex("name")));
            doctor.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
            doctor.setEmail(cursor.getString(cursor.getColumnIndex("email")));
            doctor.setPassword(cursor.getString(cursor.getColumnIndex("password")));
            doctor.setGender(cursor.getString(cursor.getColumnIndex("gender")));
            cursor.close();
            return doctor;
        }

        if (cursor != null) {
            cursor.close();
        }
        return null;
    }

    public int updateDoctor(Doctor doctor) {
        ContentValues values = new ContentValues();
        values.put("name", doctor.getName());
        values.put("phone", doctor.getPhone());
        values.put("email", doctor.getEmail());
        values.put("password", doctor.getPassword());
        values.put("gender", doctor.getGender());

        return db.update("DOCTOR", values, "practitioner_id = ?", new String[]{String.valueOf(doctor.getPractitionerId())});
    }

    public int deleteDoctor(int practitionerId) {
        return db.delete("DOCTOR", "practitioner_id = ?", new String[]{String.valueOf(practitionerId)});
    }
}
