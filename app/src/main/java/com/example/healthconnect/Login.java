package com.example.healthconnect;


import android.os.Bundle;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        EditText txtDoctorID = (EditText) findViewById(R.id.txtPractitionerID);
        EditText txtPassword = (EditText) findViewById(R.id.txtPassword);



    }
}