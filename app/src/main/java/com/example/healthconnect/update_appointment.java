package com.example.healthconnect;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.healthconnect.datamodel.Appointment;
import com.example.healthconnect.db.AppointmentDAO;

public class update_appointment extends AppCompatActivity {

    private EditText txtDateTime, txtPatientPhoneNumber, txtAppointmentReason;
    private Button btnGoBack, btnUpdate;
    private Appointment appointment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_appointment);

        txtDateTime = findViewById(R.id.txtDateTime);
        txtPatientPhoneNumber = findViewById(R.id.txtPatientPhoneNumber);
        txtAppointmentReason = findViewById(R.id.txtAppointmentReason);
        btnGoBack = findViewById(R.id.btnGoBack);
        btnUpdate = findViewById(R.id.btnUpdate);

        int appointmentID = getIntent().getIntExtra("appointmentId", -1);

        Log.d("UpdateAppointment", "appointmentId: " + appointmentID);

        AppointmentDAO appointmentDAO = new AppointmentDAO(this);

        if(appointmentID != -1){
            appointment = appointmentDAO.getAppointmentById(appointmentID);
            if(appointment != null){
                txtDateTime.setText(appointment.getDateTime());
                txtPatientPhoneNumber.setText(String.valueOf(appointment.getPatientPhn()));
                txtAppointmentReason.setText(appointment.getReason());
            }
        }
        
        btnUpdate.setOnClickListener(v -> updateAppointment());

        btnGoBack.setOnClickListener(v -> {
            Intent intent = new Intent(update_appointment.this, appointment.class);
            startActivity(intent);
        });

    }

    private void updateAppointment() {
        String dateTime = txtDateTime.getText().toString();
        String patientPhoneNumber = txtPatientPhoneNumber.getText().toString();
        String appointmentReason = txtAppointmentReason.getText().toString();

        if(dateTime.isEmpty() || patientPhoneNumber.isEmpty() || appointmentReason.isEmpty()){
            Toast.makeText(this, "Please fill empty fields", Toast.LENGTH_SHORT).show();
            return;
        }

        AppointmentDAO appointmentDAO = new AppointmentDAO(this);
        appointment.setDateTime(dateTime);
        appointment.setPatientPhn(Integer.parseInt(patientPhoneNumber));
        appointment.setReason(appointmentReason);

        try {
            long success = appointmentDAO.updateAppointment(appointment);

            if (success != -1) {
                Toast.makeText(this, "Appointment added successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(update_appointment.this, appointment.class);
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