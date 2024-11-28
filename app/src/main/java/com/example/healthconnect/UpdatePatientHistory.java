package com.example.healthconnect;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.healthconnect.datamodel.PatientHistory;
import com.example.healthconnect.db.PatientHistoryDAO;

public class UpdatePatientHistory extends AppCompatActivity {

    EditText edtPhn, edtDateTime, edtWeight, edtHeight, edtDiagnosis, edtTreatment;
    Button btnUpdatePatientHistory, btnBack, btnCancel;
    private PatientHistoryDAO patientHistoryDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_patient_history);

        edtPhn = findViewById(R.id.edtPhn);
        edtDateTime = findViewById(R.id.edtDateTime);
        edtWeight = findViewById(R.id.edtWeight);
        edtHeight = findViewById(R.id.edtHeight);
        edtDiagnosis = findViewById(R.id.edtDiagnosis);
        edtTreatment = findViewById(R.id.edtTreatments);
        btnUpdatePatientHistory = findViewById(R.id.btnUpdatePatientHistory);
        btnBack = findViewById(R.id.btnBack);
        btnCancel = findViewById(R.id.btnCancel);

        patientHistoryDAO = new PatientHistoryDAO(this);

        int historyId = getIntent().getIntExtra("historyId", -1);
        System.out.println("History Id" + historyId);
        if(historyId != -1){
            PatientHistory patientHistory = patientHistoryDAO.getPatientHistoryById(historyId);
            System.out.println("patient history" + patientHistory);
            if(patientHistory != null){
                edtPhn.setText(patientHistory.getPatientPhn());
                edtDateTime.setText(patientHistory.getDateTime());
                edtWeight.setText(String.valueOf(patientHistory.getWeight()));
                edtHeight.setText(String.valueOf(patientHistory.getHeight()));
                edtDiagnosis.setText(patientHistory.getDiagnoses());
                edtTreatment.setText(patientHistory.getTreatments());
            }

            edtPhn.setKeyListener(null);
        }

        btnUpdatePatientHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePatientHistory();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phn = edtPhn.getText().toString();
                Intent intent = new Intent(UpdatePatientHistory.this, ViewPatientHistory.class);
                intent.putExtra("phn", Integer.parseInt(phn)); // Pass the PHN back
                startActivity(intent);
                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private void updatePatientHistory(){
        String phnStr = edtPhn.getText().toString().trim();
        String dateTime = edtDateTime.getText().toString().trim();
        String weightStr = edtWeight.getText().toString().trim();
        String heightStr = edtHeight.getText().toString().trim();
        String diagnosis = edtDiagnosis.getText().toString().trim();
        String treatment = edtTreatment.getText().toString().trim();

        if(dateTime.isEmpty() || weightStr.isEmpty() || heightStr.isEmpty() || diagnosis.isEmpty() || treatment.isEmpty()){
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        double weight = Double.parseDouble(weightStr);
        double height = Double.parseDouble(heightStr);
        int phn = Integer.parseInt(phnStr);

        PatientHistory patientHistory = new PatientHistory();
        patientHistory.setDateTime(dateTime);
        patientHistory.setWeight(weight);
        patientHistory.setHeight(height);
        patientHistory.setDiagnoses(diagnosis);
        patientHistory.setTreatments(treatment);

        long result = patientHistoryDAO.updatePatientHistory(patientHistory);

        if(result != -1){
            Toast.makeText(this, "Patient History Updated Successfully", Toast.LENGTH_SHORT).show();
            finish();
        }else{
            Toast.makeText(this, "Failed to Update the Patient History Record", Toast.LENGTH_SHORT).show();
            edtDateTime.setText("");
            edtWeight.setText("");
            edtHeight.setText("");
            edtDiagnosis.setText("");
            edtTreatment.setText("");
        }

    }
}