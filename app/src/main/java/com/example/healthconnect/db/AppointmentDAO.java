package com.example.healthconnect.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;
import com.example.healthconnect.datamodel.*;

public class AppointmentDAO {
    private SQLiteDatabase db;

    public AppointmentDAO(Context context) {
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long addAppointment(Appointment appointment) {
        ContentValues values = new ContentValues();
        values.put("patient_phn", appointment.getPatientPhn());
        values.put("date_time", appointment.getDateTime());
        values.put("reason", appointment.getReason());

        return db.insert("APPOINTMENT", null, values);
    }

    public List<Appointment> getAllAppointments() {
        List<Appointment> appointments = new ArrayList<>();
        Cursor cursor = db.query("APPOINTMENT", null, null, null, null, null, null);

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

    public Appointment getAppointmentById(int id) {
        Cursor cursor = db.query("APPOINTMENT", null, "id = ?", new String[]{String.valueOf(id)}, null, null, null);

        Appointment appointment = new Appointment();

        if (cursor != null && cursor.moveToFirst()) {
            appointment.setId(cursor.getInt(cursor.getColumnIndex("id")));
            appointment.setPatientPhn(cursor.getInt(cursor.getColumnIndex("patient_phn")));
            appointment.setDateTime(cursor.getString(cursor.getColumnIndex("date_time")));
            appointment.setReason(cursor.getString(cursor.getColumnIndex("reason")));
            cursor.close();
        }

        if (cursor != null) {
            cursor.close();
        }

        return appointment;
    }

    public int updateAppointment(Appointment appointment) {
        ContentValues values = new ContentValues();
        values.put("patient_phn", appointment.getPatientPhn());
        values.put("date_time", appointment.getDateTime());
        values.put("reason", appointment.getReason());

        return db.update("APPOINTMENT", values, "id = ?", new String[]{String.valueOf(appointment.getId())});
    }

    public int deleteAppointment(int id) {
        return db.delete("APPOINTMENT", "id = ?", new String[]{String.valueOf(id)});
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
