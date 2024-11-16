package com.example.healthconnect.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "health_connect.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE DOCTOR (" +
                "practitioner_id INTEGER PRIMARY KEY, " +
                "name TEXT NOT NULL, " +
                "phone TEXT NOT NULL, " +
                "email TEXT NOT NULL, " +
                "password TEXT NOT NULL, " +
                "gender TEXT NOT NULL);");

        db.execSQL("CREATE TABLE PATIENT (" +
                "phn INTEGER PRIMARY KEY, " +
                "name TEXT NOT NULL, " +
                "birthday TEXT NOT NULL, " +
                "gender TEXT NOT NULL, " +
                "address TEXT NOT NULL, " +
                "phone TEXT NOT NULL, " +
                "practitioner_id INTEGER NOT NULL, " +
                "FOREIGN KEY (practitioner_id) REFERENCES DOCTOR(practitioner_id) " +
                "ON DELETE CASCADE);");

        db.execSQL("CREATE TABLE PATIENT_HISTORY (" +
                "history_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "patient_phn INTEGER NOT NULL, " +
                "date_time TEXT NOT NULL, " +
                "weight REAL NOT NULL, " +
                "height REAL NOT NULL, " +
                "diagnoses TEXT, " +
                "treatments TEXT, " +
                "FOREIGN KEY (patient_phn) REFERENCES PATIENT(phn) " +
                "ON DELETE CASCADE);");

        db.execSQL("CREATE TABLE HISTORY_MEDICATION (" +
                "med_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "history_id INTEGER NOT NULL, " +
                "medicine_name TEXT NOT NULL, " +
                "form TEXT NOT NULL, " +
                "strength TEXT NOT NULL, " +
                "dosage TEXT NOT NULL, " +
                "duration TEXT NOT NULL, " +
                "FOREIGN KEY (history_id) REFERENCES PATIENT_HISTORY(history_id) " +
                "ON DELETE CASCADE);");

        db.execSQL("CREATE TABLE APPOINTMENT (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "patient_phn INTEGER NOT NULL, " +
                "date_time TEXT NOT NULL, " +
                "reason TEXT, " +
                "FOREIGN KEY (patient_phn) REFERENCES PATIENT(phn) " +
                "ON DELETE CASCADE);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS APPOINTMENT");
        db.execSQL("DROP TABLE IF EXISTS HISTORY_MEDICATION");
        db.execSQL("DROP TABLE IF EXISTS PATIENT_HISTORY");
        db.execSQL("DROP TABLE IF EXISTS PATIENT");
        db.execSQL("DROP TABLE IF EXISTS DOCTOR");
        onCreate(db);
    }
}
