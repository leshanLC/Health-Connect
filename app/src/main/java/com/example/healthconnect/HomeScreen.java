package com.example.healthconnect;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import com.example.healthconnect.datamodel.Appointment;
import com.example.healthconnect.db.AppointmentDAO;
import com.example.healthconnect.db.DoctorDAO;
import com.example.healthconnect.datamodel.Doctor;
import com.example.healthconnect.db.PatientDAO;
import com.example.healthconnect.datamodel.Patient;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class HomeScreen extends AppCompatActivity {

    TextView welcomeText;
    LinearLayout patientsHistory, upcomingAppointment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_screen);

        Doctor doctor = (Doctor) getIntent().getSerializableExtra("doctor");

        welcomeText = (TextView) findViewById(R.id.tvWelcome);
        welcomeText.setText("Welcome "+ doctor.getName());
        welcomeText.setTextSize(20);
        PatientDAO patientDAO = new PatientDAO(this);
        AppointmentDAO appointmentDAO = new AppointmentDAO(this);

        patientsHistory = (LinearLayout) findViewById(R.id.ConsultHistoryLayout);
        upcomingAppointment = (LinearLayout) findViewById(R.id.AppointmentLayout);

        List<Patient> patients = patientDAO.getPatientsByPractitionerId(doctor.getPractitionerId());
        getConsultationHistory(patients);

        List<Appointment> as = appointmentDAO.getAllAppointments();
        List<Appointment> appointments = appointmentDAO.getAppointmentsByPractitionerId(doctor.getPractitionerId());
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


            patientName.setText(patient.getName());
            patientName.setTextSize(16);
            patientName.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            patientName.setPadding(20, 8, 8, 8);
            viewBtn.setText("View");
            viewBtn.setTextSize(10);
            viewBtn.setMaxWidth(50);

            TableRow.LayoutParams params = new TableRow.LayoutParams(
                    0, TableRow.LayoutParams.WRAP_CONTENT, 1.0f);

            patientName.setLayoutParams(params);
            viewBtn.setLayoutParams(params);

            tableRow.addView(patientImg);
            tableRow.addView(patientName);
            tableRow.addView(viewBtn);

            patientsHistory.addView(tableRow);
        }
    }

    public void getUpcomingAppointments(List<Appointment> appointments, PatientDAO patientDAO){
        for (int i = 0; i < 4; i++) {

            Appointment appointment = appointments.get(i);
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

            appointmentTime.setText(appointment.getDateTime());
            appointmentTime.setTextSize(16);
            appointmentTime.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            appointmentTime.setPadding(20, 15, 8, 8);

            TableRow.LayoutParams params = new TableRow.LayoutParams(
                    0, TableRow.LayoutParams.WRAP_CONTENT, 1.0f);

            patientName.setLayoutParams(params);
            appointmentTime.setLayoutParams(params);

            tableRow.addView(patientImg);
            tableRow.addView(patientName);
            tableRow.addView(appointmentTime);

            upcomingAppointment.addView(tableRow);
        }
    }
}