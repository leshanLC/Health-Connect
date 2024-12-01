package com.example.healthconnect;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.healthconnect.datamodel.Patient;
import com.example.healthconnect.db.PatientDAO;

public class ViewPatient extends AppCompatActivity {
    EditText edtPhn, edtName, edtBirthday, edtAddress, edtPractitionerID, edtPhone;
    private RadioGroup radioGroupGender;
    private RadioButton radioMale, radioFemale;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_patient);

        Button btnViewPatientHistory = (Button) findViewById(R.id.btnUpdatePatient);
        Button btnBack = (Button) findViewById(R.id.btnBack);
        edtPhn = findViewById(R.id.edtPhn);
        edtName = findViewById(R.id.edtName);
        edtBirthday = findViewById(R.id.edtBirthday);
        edtPractitionerID = findViewById(R.id.edtPractitionerID);
        edtAddress = findViewById(R.id.edtAddress);
        edtPhone = findViewById(R.id.edtPhone);
        radioGroupGender = findViewById(R.id.radioGroupGender);
        radioFemale = findViewById(R.id.radioFemale);
        radioMale = findViewById(R.id.radioMale);

        //TextView tvPatientDetails = findViewById(R.id.tvPatientDetails);
        PatientDAO patientDAO = new PatientDAO(this);

        int phn = getIntent().getIntExtra("phn", -1);
        String from = getIntent().getStringExtra("from");
        ImageView homeBtn = (ImageView) findViewById(R.id.ivHomeBtn);
        if (from!=null && from.equals("home")){
            homeBtn.setVisibility(View.VISIBLE);
            homeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(ViewPatient.this, HomeScreen.class));
                }
            });
        }


        if (phn != -1) {
            Patient patient = patientDAO.getPatientByPhn(phn);
            if (patient != null) {
                edtPhn.setText(String.valueOf(phn));
                edtName.setText(patient.getName());
                edtAddress.setText(patient.getAddress());
                edtBirthday.setText(patient.getBirthday());
                edtPhone.setText(patient.getPhone());
                edtPractitionerID.setText(String.valueOf(patient.getPractitionerId()));

                String gender = patient.getGender();

                if (gender.equalsIgnoreCase("Male")) {
                    radioMale.setChecked(true);
                } else {
                    radioFemale.setChecked(true);
                }

                edtPhn.setKeyListener(null);
                edtName.setKeyListener(null);
                edtAddress.setKeyListener(null);
                edtBirthday.setKeyListener(null);
                edtPhone.setKeyListener(null);
                edtPractitionerID.setKeyListener(null);
                radioGroupGender.setEnabled(false);
            }
        }

        btnViewPatientHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(ViewPatient.this, ViewPatientHistory.class));
                Intent intent = new Intent(ViewPatient.this, ViewPatientHistory.class);
                intent.putExtra("phn", edtPhn.getText().toString());
                startActivity(intent);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewPatient.this, PatientManagement.class));
            }
        });
    }
}