package com.example.healthconnect;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthconnect.datamodel.HistoryMedication;
import com.example.healthconnect.db.HistoryMedicationDAO;

import java.util.List;

public class medication extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_medication);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        HistoryMedicationDAO historyMedicationDAO = new HistoryMedicationDAO(this);
        List<HistoryMedication> medications = historyMedicationDAO.getAllHistoryMedications();

        MedicationAdapter adapter = new MedicationAdapter(medications);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);



        Button btnMed = findViewById(R.id.btnAdd);
        btnMed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(medication.this, add_medication.class);
                startActivity(intent);
            }
        });

    }

}