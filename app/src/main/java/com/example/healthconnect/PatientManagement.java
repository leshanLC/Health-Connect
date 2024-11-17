package com.example.healthconnect;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class PatientManagement extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_patient_management);

        SearchView patientSearchView = (SearchView) findViewById(R.id.svPatients);
        Button btnAddPatients = (Button) findViewById(R.id.btnPlus);
        Button btnTest = (Button) findViewById(R.id.btnTest);

        btnAddPatients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PatientManagement.this, TestActivity.class));
            }
        });

        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PatientManagement.this, ViewPatient.class));
            }
        });
    }
}