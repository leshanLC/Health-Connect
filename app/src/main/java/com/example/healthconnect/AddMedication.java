package com.example.healthconnect;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.healthconnect.datamodel.HistoryMedication;
import com.example.healthconnect.db.HistoryMedicationDAO;

public class AddMedication extends AppCompatActivity {

    private EditText txtMedHistoryID, txtMedName, txtMedicationDosage, txtMedicationStrength, txtMedicationForm, txtMedicationDuration;
    Button btnBack, btnAdd;
    int hisID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_medication);

        hisID = getIntent().getIntExtra("hisID", 0);

        txtMedicationDuration = findViewById(R.id.txtMedicationDuration);
        txtMedicationForm = findViewById(R.id.txtMedicationForm);
        txtMedicationStrength = findViewById(R.id.txtMedicationStrength);
        txtMedHistoryID = findViewById(R.id.txtMedHistoryID);
        txtMedName = findViewById(R.id.txtMedName);
        txtMedicationDosage = findViewById(R.id.txtMedicationDosage);
        btnBack = findViewById(R.id.btnGoBack);
        btnAdd = findViewById(R.id.btnAdd);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddMedication.this, Medication.class);
                startActivity(intent);
            }
        });

        HistoryMedicationDAO historyMedicationDAO = new HistoryMedicationDAO(this);

        btnAdd.setOnClickListener(v -> addMedication(historyMedicationDAO));


    }

    private void addMedication(HistoryMedicationDAO historyMedicationDAO) {
        if(txtMedicationDuration.getText().toString().isEmpty() || txtMedicationForm.getText().toString().isEmpty() ||
                txtMedHistoryID.getText().toString().isEmpty() || txtMedName.getText().toString().isEmpty() ||
                txtMedicationStrength.getText().toString().isEmpty()){
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int historyId = hisID;
        String medName = txtMedName.getText().toString();
        String medicationForm = txtMedicationForm.getText().toString();
        String medicationStrength = txtMedicationStrength.getText().toString();
        String medicationDosage = txtMedicationDosage.getText().toString();
        String medicationDuration = txtMedicationDuration.getText().toString();

        HistoryMedication historyMedication = new HistoryMedication();
        historyMedication.setHistoryId(historyId);
        historyMedication.setMedicineName(medName);
        historyMedication.setForm(medicationForm);
        historyMedication.setStrength(medicationStrength);
        historyMedication.setDosage(medicationDosage);
        historyMedication.setDuration(medicationDuration);

        try {
            long success = historyMedicationDAO.addHistoryMedication(historyMedication);
            if (success != -1) {
                Toast.makeText(this, "Medication added successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AddMedication.this, Medication.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Failed to add Medication", Toast.LENGTH_SHORT).show();

            }
        }catch (Exception e){
            Log.e("Medication", "Error adding Medication", e);
            Toast.makeText(this, "An error occurred: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }


    }
}