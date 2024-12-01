package com.example.healthconnect.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "health_connect.db";
    private static final int DATABASE_VERSION = 7;

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

        prepopulateData(db);
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

    private void prepopulateData(SQLiteDatabase db) {
        // Insert doctors
        db.execSQL("INSERT INTO DOCTOR VALUES (1, 'Dr. John Doe', '1234567890', 'john.doe@example.com', 'password123', 'Male');");
        db.execSQL("INSERT INTO DOCTOR VALUES (2, 'Dr. Jane Smith', '0987654321', 'jane.smith@example.com', 'password456', 'Female');");

        // Insert patients with varying criteria for each doctor
        String[] patientDetails = {
                "(1, 'Alice Jay', '2010-05-12', 'Female', '456 Elm St', '1231111111', 1)", // Child (Female)
                "(2, 'Bob Dilan', '1985-07-21', 'Male', '789 Maple Ave', '1232222222', 1)",  // Adult (Male)
                "(3, 'Clara Ian', '2015-02-18', 'Female', '321 Oak St', '1233333333', 1)", // Child (Female)
                "(4, 'David Lam', '1990-03-30', 'Male', '654 Pine Rd', '1234444444', 1)",  // Adult (Male)
                "(5, 'Eva Kim', '2005-10-01', 'Female', '987 Birch Ln', '1235555555', 1)", // Teen (Female)

                "(6, 'Frank Sue', '2008-06-15', 'Male', '123 Willow Dr', '9871111111', 2)", // Child (Male)
                "(7, 'Grace Dipp', '1995-08-23', 'Female', '456 Cedar Ct', '9872222222', 2)", // Adult (Female)
                "(8, 'Hank Tom', '2012-11-11', 'Male', '789 Spruce St', '9873333333', 2)",  // Child (Male)
                "(9, 'Ivy Lee', '1988-12-20', 'Female', '321 Aspen Ln', '9874444444', 2)",  // Adult (Female)
                "(10, 'Jack Ray', '2003-09-05', 'Male', '654 Fir Pl', '9875555555', 2)"    // Teen (Male)
        };

        for (String patient : patientDetails) {
            db.execSQL("INSERT INTO PATIENT VALUES " + patient + ";");

            // Extract patient PHN from the details
            String[] parts = patient.split(",");
            int phn = Integer.parseInt(parts[0].replace("(", "").trim());

            // Insert one appointment per patient
            db.execSQL("INSERT INTO APPOINTMENT (patient_phn, date_time, reason) VALUES (" + phn + ", '2024-01-01 10:00:00', 'Routine Checkup');");

            // Insert five patient histories per patient
            for (int j = 1; j <= 5; j++) {
                db.execSQL("INSERT INTO PATIENT_HISTORY (patient_phn, date_time, weight, height, diagnoses, treatments) " +
                        "VALUES (" + phn + ", '2024-01-" + j + " 10:00:00', " + (60 + j) + ", " + (160 + j) + ", 'Diagnosis " + j + "', 'Treatment " + j + "');");

                // Insert two to three medications per history
                for (int k = 1; k <= 3; k++) {
                    db.execSQL("INSERT INTO HISTORY_MEDICATION (history_id, medicine_name, form, strength, dosage, duration) " +
                            "VALUES ((SELECT MAX(history_id) FROM PATIENT_HISTORY), 'Medicine " + k + "', 'Tablet', '500mg', '1 tablet daily', '7 days');");
                }
            }
        }
    }


}
