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

import com.example.healthconnect.datamodel.Patient;
import com.example.healthconnect.db.PatientDAO;

public class TestActivity extends AppCompatActivity {

    private EditText edtPhn, edtName, edtBirthday, edtAddress, edtPhone, edtPractitionerId;
    private RadioGroup radioGroupGender;
    private RadioButton radioMale, radioFemale;
    private Button btnSavePatient, btnBack, btnCancel;
    private PatientDAO patientDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_test);

        btnSavePatient = findViewById(R.id.btnAddPatient);
        btnBack = findViewById(R.id.btnBack);
        btnCancel = findViewById(R.id.btnCancel);
        edtPhn = findViewById(R.id.edtPhn);
        edtName = findViewById(R.id.edtName);
        edtBirthday = findViewById(R.id.edtBirthday);
        edtAddress = findViewById(R.id.edtAddress);
        edtPhone = findViewById(R.id.edtPhone);
        edtPractitionerId = findViewById(R.id.edtPractitionerID);
        radioGroupGender = findViewById(R.id.radioGroupGender);
        radioFemale = findViewById(R.id.radioFemale);
        radioMale = findViewById(R.id.radioMale);

        patientDAO = new PatientDAO(this);

        btnSavePatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePatient();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TestActivity.this, PatientManagement.class));
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TestActivity.this, PatientManagement.class));
            }
        });
    }

    private void savePatient(){
        String phnStr = edtPhn.getText().toString().trim();
        String name = edtName.getText().toString().trim();
        String birthday = edtBirthday.getText().toString().trim();
        String address = edtAddress.getText().toString().trim();
        String phone = edtPhone.getText().toString().trim();
        String practitionerIdStr = edtPractitionerId.getText().toString().trim();
        String gender;

        if(radioMale.isChecked()) {
            gender = "Male";
        }else if(radioFemale.isChecked()){
            gender="Female";
        }else{
            gender="";
        }

        if(phnStr.isEmpty() || name.isEmpty() || birthday.isEmpty() || address.isEmpty() || phone.isEmpty() || practitionerIdStr.isEmpty() || gender.isEmpty()){
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        int phn = Integer.parseInt(phnStr);
        int practitionerId = Integer.parseInt(practitionerIdStr);

        Patient patient = new Patient();
        patient.setPhn(phn);
        patient.setName(name);
        patient.setAddress(address);
        patient.setBirthday(birthday);
        patient.setGender(gender);
        patient.setPhone(phone);
        patient.setPractitionerId(practitionerId);

        long result = patientDAO.addPatient(patient);

        if(result != -1){
            Toast.makeText(this, "Patient Saved Successfully", Toast.LENGTH_SHORT).show();
            finish();
        }else{
            Toast.makeText(this, "Failed to Save the Patient", Toast.LENGTH_SHORT).show();
            edtPhn.setText("");
            edtName.setText("");
            edtBirthday.setText("");
            edtAddress.setText("");
            edtPhone.setText("");
            edtPractitionerId.setText("");
            radioGroupGender.clearCheck();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}