package com.example.healthconnect;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.example.healthconnect.db.DoctorDAO;
import com.example.healthconnect.datamodel.Doctor;

public class SignUp extends AppCompatActivity {
    EditText name, practitionerID, phone, email, password;
    String doctorName, doctorPhone, doctorEmail, doctorPassword, doctorGender;
    int doctorId;
    RadioGroup gender;
    RadioButton selectedGender;
    Button btnSignIn, btnSignUp;
    DoctorDAO doctorDAO;
    Doctor doctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);

        name = (EditText) findViewById(R.id.txtDoctorName);
        practitionerID = (EditText) findViewById(R.id.txtDoctorID);
        phone = (EditText) findViewById(R.id.txtPhone);
        email = (EditText) findViewById(R.id.txtDoctorEmail);
        password = (EditText) findViewById(R.id.txtPassword);
        gender = (RadioGroup) findViewById(R.id.rbtnGenger);

        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);

        doctorDAO = new DoctorDAO(SignUp.this);
        doctor = new Doctor();

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUp.this, Login.class));
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doctorName = name.getText().toString();
                doctorPhone = phone.getText().toString();
                doctorEmail = email.getText().toString();
                doctorPassword = password.getText().toString();
                int genderid = gender.getCheckedRadioButtonId();
                selectedGender = (RadioButton) findViewById(genderid);
                doctorGender = selectedGender.getText().toString();
                String docId = practitionerID.getText().toString();

                if (doctorName.isEmpty() || docId.isEmpty() || doctorPhone.isEmpty() || doctorEmail.isEmpty() || doctorPassword.isEmpty() || doctorGender.isEmpty()){
                    Toast.makeText(SignUp.this, "Error: one or more field is empty!", Toast.LENGTH_SHORT).show();
                } else {
                    doctor.setPractitionerId(Integer.parseInt(docId));
                    doctor.setName(doctorName);
                    doctor.setPhone(doctorPhone);
                    doctor.setEmail(doctorEmail);
                    doctor.setPassword(doctorPassword);
                    doctor.setGender(doctorGender);
                    long insertDoctor = doctorDAO.addDoctor(doctor);
                    if (insertDoctor == -1){
                        Toast.makeText(SignUp.this, "Error: insertion failed!", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(SignUp.this, "Success: insertion succeed!", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }
}