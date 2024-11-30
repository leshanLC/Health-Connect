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

import com.example.healthconnect.datamodel.Appointment;
import com.example.healthconnect.db.AppointmentDAO;

public class add_appointment extends AppCompatActivity {

    private EditText txtPatientPhoneNumber;
    private EditText txtDateTime;
    private EditText txtReason;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_appointment);

        txtDateTime = findViewById(R.id.txtDateTime);
        txtReason = findViewById(R.id.txtAppointmentReason);
        txtPatientPhoneNumber = findViewById(R.id.txtPatientPhoneNumber);
        Button btnAddAppointment = findViewById(R.id.btnAddAppointment);

        Button btnGoBack = findViewById(R.id.btnGoBack);
        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(add_appointment.this, appointment.class);
                startActivity(intent);
            }
        });

        AppointmentDAO appointmentDAO = new AppointmentDAO(this);

        btnAddAppointment.setOnClickListener(v -> addAppointment(appointmentDAO));

    }

    private void addAppointment(AppointmentDAO appointmentDAO) {

        if(txtPatientPhoneNumber.getText().toString().isEmpty() || txtDateTime.getText().toString().isEmpty() || txtReason.getText().toString().isEmpty()){
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int patientPhn = Integer.parseInt(txtPatientPhoneNumber.getText().toString());
        String dateTime = txtDateTime.getText().toString();
        String reason = txtReason.getText().toString();

        Appointment appointment = new Appointment();
        appointment.setPatientPhn(patientPhn);
        appointment.setDateTime(dateTime);
        appointment.setReason(reason);

        try {
            long success = appointmentDAO.addAppointment(appointment);

            if (success != -1) {
                Toast.makeText(this, "Appointment added successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(add_appointment.this, appointment.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Failed to add appointment", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.e("AddAppointment", "Error adding appointment", e);
            Toast.makeText(this, "An error occurred: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }


    }


}