package com.example.healthconnect.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;
import com.example.healthconnect.datamodel.*;

public class HistoryMedicationDAO {
    private SQLiteDatabase db;

    public HistoryMedicationDAO(Context context) {
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long addHistoryMedication(HistoryMedication medication) {
        ContentValues values = new ContentValues();
        values.put("history_id", medication.getHistoryId());
        values.put("medicine_name", medication.getMedicineName());
        values.put("form", medication.getForm());
        values.put("strength", medication.getStrength());
        values.put("dosage", medication.getDosage());
        values.put("duration", medication.getDuration());

        return db.insert("HISTORY_MEDICATION", null, values);
    }

    public List<HistoryMedication> getAllHistoryMedications() {
        List<HistoryMedication> medications = new ArrayList<>();
        Cursor cursor = db.query("HISTORY_MEDICATION", null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                HistoryMedication medication = new HistoryMedication();
                medication.setMedId(cursor.getInt(cursor.getColumnIndex("med_id")));
                medication.setHistoryId(cursor.getInt(cursor.getColumnIndex("history_id")));
                medication.setMedicineName(cursor.getString(cursor.getColumnIndex("medicine_name")));
                medication.setForm(cursor.getString(cursor.getColumnIndex("form")));
                medication.setStrength(cursor.getString(cursor.getColumnIndex("strength")));
                medication.setDosage(cursor.getString(cursor.getColumnIndex("dosage")));
                medication.setDuration(cursor.getString(cursor.getColumnIndex("duration")));
                medications.add(medication);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return medications;
    }

    public HistoryMedication getHistoryMedicationById(int medId) {
        Cursor cursor = db.query("HISTORY_MEDICATION", null, "med_id = ?", new String[]{String.valueOf(medId)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            HistoryMedication medication = new HistoryMedication();
            medication.setMedId(cursor.getInt(cursor.getColumnIndex("med_id")));
            medication.setHistoryId(cursor.getInt(cursor.getColumnIndex("history_id")));
            medication.setMedicineName(cursor.getString(cursor.getColumnIndex("medicine_name")));
            medication.setForm(cursor.getString(cursor.getColumnIndex("form")));
            medication.setStrength(cursor.getString(cursor.getColumnIndex("strength")));
            medication.setDosage(cursor.getString(cursor.getColumnIndex("dosage")));
            medication.setDuration(cursor.getString(cursor.getColumnIndex("duration")));
            cursor.close();
            return medication;
        }

        if (cursor != null) {
            cursor.close();
        }
        return null;
    }

    public int updateHistoryMedication(HistoryMedication medication) {
        ContentValues values = new ContentValues();
        values.put("history_id", medication.getHistoryId());
        values.put("medicine_name", medication.getMedicineName());
        values.put("form", medication.getForm());
        values.put("strength", medication.getStrength());
        values.put("dosage", medication.getDosage());
        values.put("duration", medication.getDuration());

        return db.update("HISTORY_MEDICATION", values, "med_id = ?", new String[]{String.valueOf(medication.getMedId())});
    }

    public int deleteHistoryMedication(int medId) {
        return db.delete("HISTORY_MEDICATION", "med_id = ?", new String[]{String.valueOf(medId)});
    }

    public List<HistoryMedication> getMedicationsByHistoryId(int historyId) {
        List<HistoryMedication> medications = new ArrayList<>();
        Cursor cursor = db.query(
                "HISTORY_MEDICATION",
                null,
                "history_id = ?",
                new String[]{String.valueOf(historyId)},
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            do {
                HistoryMedication medication = new HistoryMedication();
                medication.setMedId(cursor.getInt(cursor.getColumnIndex("med_id")));
                medication.setHistoryId(cursor.getInt(cursor.getColumnIndex("history_id")));
                medication.setMedicineName(cursor.getString(cursor.getColumnIndex("medicine_name")));
                medication.setForm(cursor.getString(cursor.getColumnIndex("form")));
                medication.setStrength(cursor.getString(cursor.getColumnIndex("strength")));
                medication.setDosage(cursor.getString(cursor.getColumnIndex("dosage")));
                medication.setDuration(cursor.getString(cursor.getColumnIndex("duration")));
                medications.add(medication);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return medications;
    }

}
