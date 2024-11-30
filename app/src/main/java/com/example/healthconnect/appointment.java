package com.example.healthconnect;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthconnect.datamodel.Appointment;
import com.example.healthconnect.db.AppointmentDAO;

import java.util.List;

public class appointment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_appointment);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        AppointmentDAO appointmentDAO = new AppointmentDAO(this);
        List<Appointment> appointments = appointmentDAO.getAllAppointments();

        AppointmentAdapter adapter = new AppointmentAdapter(appointments);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        Button btnAddAppointment = findViewById(R.id.btnAddAppointment);
        btnAddAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(appointment.this, add_appointment.class);
                startActivity(intent);
            }
        });

    }
}