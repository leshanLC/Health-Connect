package com.example.healthconnect;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.healthconnect.db.AppointmentDAO;

public class ViewAppointment extends AppCompatActivity {

    private TextView txtPatientPhn, txtDateTime, txtReason;
    Button  btnUpdate, btnDelete, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_appointment);

        txtPatientPhn = findViewById(R.id.txtPatientPhoneNumber);
        txtDateTime = findViewById(R.id.txtDateTime);
        txtReason = findViewById(R.id.txtAppointmentReason);


        int appointmentId = getIntent().getIntExtra("appointmentId", -1);
        if (appointmentId != -1) {
            loadAppointment(appointmentId);
        }else{
            Toast.makeText(this, "Invalid Appointment ID", Toast.LENGTH_SHORT).show();
        }

        String from = getIntent().getStringExtra("from");
        ImageView homeBtn = (ImageView) findViewById(R.id.ivHomeBtn);
        if (from!=null && from.equals("home")){
            homeBtn.setVisibility(View.VISIBLE);
            homeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(ViewAppointment.this, HomeScreen.class));
                }
            });
        }

        btnUpdate = findViewById(R.id.btnUpdateAppointment);
        btnDelete = findViewById(R.id.btnDeleteAppointment);
        btnBack = findViewById(R.id.btnGoBack);

        btnDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(v.getContext()).setTitle("Delete Appointment").setMessage("Are you sure you want to delete this Appointment?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        AppointmentDAO appointmentDAO = new AppointmentDAO(v.getContext());
                        appointmentDAO.deleteAppointment(appointmentId);
                        Intent intent = new Intent(ViewAppointment.this, Appointment.class);
                        startActivity(intent);

                    }).setNegativeButton("No", null).show();
        });

        btnUpdate.setOnClickListener(v -> {
            Intent intent = new Intent(ViewAppointment.this, UpdateAppointment.class);
            intent.putExtra("appointmentId", appointmentId);
            startActivity(intent);
        });

        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(ViewAppointment.this, Appointment.class);
            startActivity(intent);
        });


    }

    private void loadAppointment(int appointmentId) {
        AppointmentDAO appointmentDAO = new AppointmentDAO(this);
        com.example.healthconnect.datamodel.Appointment appointment = appointmentDAO.getAppointmentById(appointmentId);

        if (appointment != null) {
            txtPatientPhn.setText(String.valueOf(appointment.getPatientPhn()));
            txtDateTime.setText(appointment.getDateTime());
            txtReason.setText(appointment.getReason());
        }else {
            Toast.makeText(this, "Appointment not found", Toast.LENGTH_SHORT).show();
        }
    }
}