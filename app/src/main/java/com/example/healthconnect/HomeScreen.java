package com.example.healthconnect;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


import com.example.healthconnect.db.AppointmentDAO;
import com.example.healthconnect.datamodel.Doctor;
import com.example.healthconnect.db.PatientDAO;
import com.example.healthconnect.datamodel.Patient;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HomeScreen extends AppCompatActivity {

    TextView welcomeText;
    LinearLayout patientsHistory, upcomingAppointment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_screen);

        ImageView ivLogOut = (ImageView)findViewById(R.id.ivLogOut);
        Button mngPatient = (Button) findViewById(R.id.btnManagePatient);
        Button mngAppointment = (Button) findViewById(R.id.btnManageAppointment);

        ivLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeScreen.this, Login.class));
            }
        });



        mngAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeScreen.this, Appointment.class));
            }
        });

        Doctor doctor = (Doctor) getIntent().getSerializableExtra("doctor");

        welcomeText = (TextView) findViewById(R.id.tvWelcome);
        welcomeText.setText("Welcome Dr. "+ doctor.getName());
        welcomeText.setTextSize(22);
        PatientDAO patientDAO = new PatientDAO(this);
        AppointmentDAO appointmentDAO = new AppointmentDAO(this);

        patientsHistory = (LinearLayout) findViewById(R.id.ConsultHistoryLayout);
        upcomingAppointment = (LinearLayout) findViewById(R.id.AppointmentLayout);

        mngPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeScreen.this, PatientManagement.class);
                intent.putExtra("doctorID",doctor.getPractitionerId());
                startActivity(intent);
            }
        });

        List<Patient> patients = patientDAO.getPatientsByPractitionerId(doctor.getPractitionerId());
        getConsultationHistory(patients);

        List<com.example.healthconnect.datamodel.Appointment> as = appointmentDAO.getAllAppointments();
        List<com.example.healthconnect.datamodel.Appointment> appointments = appointmentDAO.getAppointmentsByPractitionerId(doctor.getPractitionerId());
        getUpcomingAppointments(appointments, patientDAO);

    }

    public void getConsultationHistory(List<Patient> patients){
        for (int i = 0; i < 4; i++) {
            Patient patient = patients.get(i);
            TableRow tableRow = new TableRow(this);
            TextView patientName = new TextView(this);
            ImageView patientImg = new ImageView(this);
            Button viewBtn = new Button(this);

            String gender = patient.getGender().toLowerCase();
            String ageType;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date birthDate = new Date();
            try {
                birthDate = dateFormat.parse(patient.getBirthday());
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
            Calendar today = Calendar.getInstance();
            Calendar birthDay = Calendar.getInstance();
            birthDay.setTime(birthDate);
            int age = today.get(Calendar.YEAR) - birthDay.get(Calendar.YEAR);

            // Adjust if necessary based on the day/month
            if (today.get(Calendar.DAY_OF_YEAR) > birthDay.get(Calendar.DAY_OF_YEAR)) {
                age--;
            }

            if (age > 18) {
                ageType = "adult";
            } else {
                ageType = "child";
            }
            String drawableImg = ageType + "_" + gender;
            patientImg.setLayoutParams(new TableRow.LayoutParams(150, 150));
            int resId = getResources().getIdentifier(drawableImg, "drawable", getPackageName());

            if (resId != 0) {
                patientImg.setImageResource(resId);
            }


            Drawable blueBtn = getDrawable(R.drawable.blue_button);
            patientName.setText(patient.getName());
            patientName.setTextSize(16);
            patientName.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            patientName.setPadding(20, 8, 8, 8);
            viewBtn.setText("View");
            viewBtn.setTextSize(16);
            viewBtn.setBackground(blueBtn);
            viewBtn.setTextColor(Color.WHITE);
            viewBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(HomeScreen.this, ViewPatient.class);
                    intent.putExtra("phn", patient.getPhn());
                    intent.putExtra("from", "home");
                    startActivity(intent);
                }
            });

            TableRow.LayoutParams params = new TableRow.LayoutParams(
                    0, TableRow.LayoutParams.WRAP_CONTENT, 1.0f);

            patientName.setLayoutParams(params);
            viewBtn.setLayoutParams(params);

            tableRow.addView(patientImg);
            tableRow.addView(patientName);
            tableRow.addView(viewBtn);
            tableRow.setPadding(0,10,0,10);

            patientsHistory.addView(tableRow);
        }
    }

    public void getUpcomingAppointments(List<com.example.healthconnect.datamodel.Appointment> appointments, PatientDAO patientDAO){
        for (int i = 0; i < 4; i++) {

            com.example.healthconnect.datamodel.Appointment appointment = appointments.get(i);
            TableRow tableRow = new TableRow(this);
            TextView patientName = new TextView(this);
            ImageView patientImg = new ImageView(this);
            TextView appointmentTime = new TextView(this);


            Patient patient = patientDAO.getPatientByPhn(appointment.getPatientPhn());

            String gender = patient.getGender().toLowerCase();
            String ageType;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date birthDate = new Date();
            try {
                birthDate = dateFormat.parse(patient.getBirthday());
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
            Calendar today = Calendar.getInstance();
            Calendar birthDay = Calendar.getInstance();
            birthDay.setTime(birthDate);
            int age = today.get(Calendar.YEAR) - birthDay.get(Calendar.YEAR);

            // Adjust if necessary based on the day/month
            if (today.get(Calendar.DAY_OF_YEAR) > birthDay.get(Calendar.DAY_OF_YEAR)) {
                age--;
            }

            if (age > 18) {
                ageType = "adult";
            } else {
                ageType = "child";
            }
            String drawableImg = ageType + "_" + gender;
            patientImg.setLayoutParams(new TableRow.LayoutParams(150, 150));
            int resId = getResources().getIdentifier(drawableImg, "drawable", getPackageName());

            if (resId != 0) {
                patientImg.setImageResource(resId);
            }


            patientName.setText(patient.getName());
            patientName.setTextSize(16);
            patientName.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            patientName.setPadding(20, 15, 8, 8);

            Drawable greenBackground = getDrawable(R.drawable.green_background);
            appointmentTime.setText(appointment.getDateTime());
            appointmentTime.setTextSize(16);
            appointmentTime.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            appointmentTime.setBackground(greenBackground);
            appointmentTime.setTextColor(Color.WHITE);
            appointmentTime.setPadding(20, 15, 8, 8);
            appointmentTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(HomeScreen.this, ViewAppointment.class);
                    intent.putExtra("appointmentId", appointment.getId());
                    intent.putExtra("from", "home");
                    startActivity(intent);
                }
            });

            TableRow.LayoutParams params = new TableRow.LayoutParams(
                    0, TableRow.LayoutParams.WRAP_CONTENT, 1.0f);

            patientName.setLayoutParams(params);
            appointmentTime.setLayoutParams(params);

            tableRow.addView(patientImg);
            tableRow.addView(patientName);
            tableRow.addView(appointmentTime);
            tableRow.setPadding(0,10,0,10);

            upcomingAppointment.addView(tableRow);
        }
    }
}