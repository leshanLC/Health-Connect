package com.example.healthconnect;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.healthconnect.datamodel.PatientHistory;
import com.example.healthconnect.db.PatientHistoryDAO;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddPatientHistory extends AppCompatActivity {
    EditText edtPhn, edtDateTime, edtWeight, edtHeight, edtDiagnosis, edtTreatment;
    Button btnAddPatientHistory, btnCancel, btnBack;
    PatientHistoryDAO patientHistoryDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_patient_history);

        btnBack = findViewById(R.id.btnBack);
        btnCancel = findViewById(R.id.btnCancel);
        btnAddPatientHistory = findViewById(R.id.btnAddPatientHistory);
        edtPhn = findViewById(R.id.edtPhn);
        edtDateTime = findViewById(R.id.edtDateTime);
        edtWeight = findViewById(R.id.edtWeight);
        edtHeight = findViewById(R.id.edtHeight);
        edtDiagnosis = findViewById(R.id.edtDiagnosis);
        edtTreatment = findViewById(R.id.edtTreatments);

        patientHistoryDAO = new PatientHistoryDAO(this);

        edtPhn.setText(getIntent().getStringExtra("phn"));
        edtPhn.setKeyListener(null);

        btnAddPatientHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPatientHistory();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddPatientHistory.this, ViewPatientHistory.class));
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddPatientHistory.this, ViewPatientHistory.class));
            }
        });

        edtDateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create and show the DatePicker dialog
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(year, monthOfYear, dayOfMonth);

                                // Now open the TimePicker dialog
                                TimePickerDialog tpd = TimePickerDialog.newInstance(
                                        new TimePickerDialog.OnTimeSetListener() {
                                            @Override
                                            public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
                                                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                                calendar.set(Calendar.MINUTE, minute);
                                                calendar.set(Calendar.SECOND, second);

                                                // Format the selected date and time
                                                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
                                                edtDateTime.setText(sdf.format(calendar.getTime()));
                                            }
                                        },
                                        calendar.get(Calendar.HOUR_OF_DAY),
                                        calendar.get(Calendar.MINUTE),
                                        false // 24-hour format, set to 'true' for 12-hour format
                                );
                                tpd.show(getSupportFragmentManager(), "TimePickerDialog");
                            }
                        },
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getSupportFragmentManager(), "DatePickerDialog");
            }
        });
    }

    private void addPatientHistory(){
        String phnStr = edtPhn.getText().toString().trim();
        String dateTime = edtDateTime.getText().toString().trim();
        String weightStr = edtWeight.getText().toString().trim();
        String heightStr = edtHeight.getText().toString().trim();
        String diagnosis = edtDiagnosis.getText().toString().trim();
        String treatment = edtTreatment.getText().toString().trim();

        if(phnStr.isEmpty() || dateTime.isEmpty() || diagnosis.isEmpty() || treatment.isEmpty()){
            Toast.makeText(this, "All mandatory fields must be given a value", Toast.LENGTH_SHORT).show();
            return;
        }

        int phn = Integer.parseInt(phnStr);
        int weight = Integer.parseInt(weightStr);
        int height = Integer.parseInt(heightStr);

        PatientHistory patientHistory = new PatientHistory();
        patientHistory.setPatientPhn(phn);
        patientHistory.setDateTime(dateTime);
        patientHistory.setWeight(weight);
        patientHistory.setHeight(height);
        patientHistory.setDiagnoses(diagnosis);
        patientHistory.setTreatments(treatment);

        long result = patientHistoryDAO.addPatientHistory(patientHistory);

        if(result != -1){
            Toast.makeText(this, "Patient history added successfully", Toast.LENGTH_SHORT).show();
            finish();
        }else{
            Toast.makeText(this, "Failed to add the history record", Toast.LENGTH_SHORT).show();
            edtPhn.setText("");
            edtDateTime.setText("");
            edtWeight.setText("");
            edtHeight.setText("");
            edtDiagnosis.setText("");
            edtTreatment.setText("");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}