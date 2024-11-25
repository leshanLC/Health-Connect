package com.example.healthconnect;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthconnect.datamodel.Patient;
import com.example.healthconnect.db.PatientDAO;

import java.util.ArrayList;
import java.util.List;

public class PatientManagement extends AppCompatActivity {

    private RecyclerView recyclerViewPatients;
    private PatientAdapter patientAdapter;
    private PatientDAO patientDAO;
    private EditText edtSeachPatients;
    private Button btnAddPatients;
    private List<Patient> allPatients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_patient_management);

        recyclerViewPatients = findViewById(R.id.recyclerViewPatients);
        recyclerViewPatients.setLayoutManager(new LinearLayoutManager(this));
        edtSeachPatients = findViewById(R.id.edtSearchPatient);
        btnAddPatients = (Button) findViewById(R.id.btnAddPatient);

        patientDAO = new PatientDAO(this);

        loadPatients();

        //SearchView patientSearchView = (SearchView) findViewById(R.id.svPatients);

        btnAddPatients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PatientManagement.this, TestActivity.class));
            }
        });

        edtSeachPatients.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchPatients(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });


    }

    private void searchPatients(String query) {
        List<Patient> filteredPatients = new ArrayList<>();
        for (Patient patient : allPatients) {
            if (patient.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredPatients.add(patient);
            }
        }

        // Update adapter with filtered list
        patientAdapter = new PatientAdapter(this, filteredPatients, patientDAO);
        recyclerViewPatients.setAdapter(patientAdapter);
    }

    private void loadPatients() {
        List<Patient> patientList = patientDAO.getAllPatients();
        if (patientList.isEmpty()) {
            Toast.makeText(this, "No Registered Patients", Toast.LENGTH_SHORT).show();
        }
        patientAdapter = new PatientAdapter(this, patientList, patientDAO);
        recyclerViewPatients.setAdapter(patientAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadPatients();
    }

}