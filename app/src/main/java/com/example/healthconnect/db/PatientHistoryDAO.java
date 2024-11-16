package com.example.healthconnect.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;
import com.example.healthconnect.datamodel.*;

public class PatientHistoryDAO {
    private SQLiteDatabase db;

    public PatientHistoryDAO(Context context) {
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long addPatientHistory(PatientHistory history) {
        ContentValues values = new ContentValues();
        values.put("patient_phn", history.getPatientPhn());
        values.put("date_time", history.getDateTime());
        values.put("weight", history.getWeight());
        values.put("height", history.getHeight());
        values.put("diagnoses", history.getDiagnoses());
        values.put("treatments", history.getTreatments());

        return db.insert("PATIENT_HISTORY", null, values);
    }

    public List<PatientHistory> getAllPatientHistories() {
        List<PatientHistory> histories = new ArrayList<>();
        Cursor cursor = db.query("PATIENT_HISTORY", null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                PatientHistory history = new PatientHistory();
                history.setHistoryId(cursor.getInt(cursor.getColumnIndex("histry_id")));
                history.setPatientPhn(cursor.getInt(cursor.getColumnIndex("patient_phn")));
                history.setDateTime(cursor.getString(cursor.getColumnIndex("date_time")));
                history.setWeight(cursor.getDouble(cursor.getColumnIndex("weight")));
                history.setHeight(cursor.getDouble(cursor.getColumnIndex("height")));
                history.setDiagnoses(cursor.getString(cursor.getColumnIndex("diagnoses")));
                history.setTreatments(cursor.getString(cursor.getColumnIndex("treatments")));
                histories.add(history);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return histories;
    }

    public PatientHistory getPatientHistoryById(int historyId) {
        Cursor cursor = db.query("PATIENT_HISTORY", null, "histry_id = ?", new String[]{String.valueOf(historyId)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            PatientHistory history = new PatientHistory();
            history.setHistoryId(cursor.getInt(cursor.getColumnIndex("histry_id")));
            history.setPatientPhn(cursor.getInt(cursor.getColumnIndex("patient_phn")));
            history.setDateTime(cursor.getString(cursor.getColumnIndex("date_time")));
            history.setWeight(cursor.getDouble(cursor.getColumnIndex("weight")));
            history.setHeight(cursor.getDouble(cursor.getColumnIndex("height")));
            history.setDiagnoses(cursor.getString(cursor.getColumnIndex("diagnoses")));
            history.setTreatments(cursor.getString(cursor.getColumnIndex("treatments")));
            cursor.close();
            return history;
        }

        if (cursor != null) {
            cursor.close();
        }
        return null;
    }

    public int updatePatientHistory(PatientHistory history) {
        ContentValues values = new ContentValues();
        values.put("patient_phn", history.getPatientPhn());
        values.put("date_time", history.getDateTime());
        values.put("weight", history.getWeight());
        values.put("height", history.getHeight());
        values.put("diagnoses", history.getDiagnoses());
        values.put("treatments", history.getTreatments());

        return db.update("PATIENT_HISTORY", values, "histry_id = ?", new String[]{String.valueOf(history.getHistoryId())});
    }

    public int deletePatientHistory(int historyId) {
        return db.delete("PATIENT_HISTORY", "histry_id = ?", new String[]{String.valueOf(historyId)});
    }
}
