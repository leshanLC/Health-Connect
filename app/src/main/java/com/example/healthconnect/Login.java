package com.example.healthconnect;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.healthconnect.db.DoctorDAO;
import com.example.healthconnect.datamodel.Doctor;

import java.util.List;


public class Login extends AppCompatActivity {
    EditText txtDoctorID, txtPassword;
    Button btnSignIn, btnSignUp;
    DoctorDAO doctorDAO;
    Doctor doctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        txtDoctorID = (EditText) findViewById(R.id.txtPractitionerID);
        txtPassword = (EditText) findViewById(R.id.txtPassword);

        btnSignIn = (Button) findViewById(R.id.btnLogin);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);

        doctorDAO = new DoctorDAO(Login.this);
        doctor = new Doctor();

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, SignUp.class));
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtDoctorID.getText().toString().isEmpty() || txtPassword.getText().toString().isEmpty()){
                    Toast.makeText(Login.this, "Error: Invalid Practitioner ID or Password Empty!", Toast.LENGTH_SHORT).show();
                }else {
                    int doctorID = Integer.parseInt(txtDoctorID.getText().toString());
                    doctor = doctorDAO.getDoctorById(doctorID);
                    if (doctor == null){
                        Toast.makeText(Login.this, "Error: Invalid Practitioner ID!", Toast.LENGTH_SHORT).show();
                    }
                    if (doctor != null && txtPassword.getText().toString().equals(doctor.getPassword())){
                        Intent intent = new Intent(Login.this, HomeScreen.class);
                        intent.putExtra("doctor",doctor);
                        startActivity(intent);
                    }else {
                        Toast.makeText(Login.this, "Error: Invalid Password!", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });
    }

}