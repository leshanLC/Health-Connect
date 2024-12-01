package com.example.healthconnect;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.healthconnect.db.AppointmentDAO;

public class EmailReminder extends AppCompatActivity {

    private TextView txtReceiver, txtSubject, txtBody;
    Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_email_reminder);

        txtReceiver = findViewById(R.id.txtReceiverAddress);
        txtSubject = findViewById(R.id.txtSubject);
        txtBody = findViewById(R.id.txtBody);
        btnSend = findViewById(R.id.btnSend);

        int appointmentId = getIntent().getIntExtra("appointmentId", -1);

        AppointmentDAO appointmentDAO = new AppointmentDAO(this);
        com.example.healthconnect.datamodel.Appointment appointment = appointmentDAO.getAppointmentById(appointmentId);

        if (appointmentId != -1) {
            String body = "Appointment details \n\n" +
                    "Appointment ID: " + appointment.getId() + "\n" +
                    "Date: " + appointment.getDateTime() + "\n" +
                    "Reason: " + appointment.getReason();

            txtSubject.setText(R.string.txtReminderSubject);
            txtBody.setText(body);



        }else{
            Toast.makeText(this, "Invalid Appointment ID", Toast.LENGTH_SHORT).show();
        }

        btnSend.setOnClickListener(v -> sendEmail());

    }
    private void sendEmail() {
        String recipientEmail = txtReceiver.getText().toString();
        String subject = txtSubject.getText().toString();
        String messageBody = txtBody.getText().toString();
        Log.d("EmailReminder", "Recipient Email: " + recipientEmail);
        Log.d("EmailReminder", "Subject: " + subject);
        Log.d("EmailReminder", "Message Body: " + messageBody);


        if(recipientEmail.isEmpty() || subject.isEmpty() || messageBody.isEmpty()){
            Toast.makeText(this, "Please fill empty fields", Toast.LENGTH_SHORT).show();
            return;
        }

        MailAPI mailAPI = new MailAPI();
        mailAPI.sendEmail(recipientEmail, subject, messageBody);

        Toast.makeText(this, "Email sending in progress...", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, Appointment.class);
        startActivity(intent);


    }
}