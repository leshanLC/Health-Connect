package com.example.healthconnect;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.healthconnect.datamodel.Patient;
import com.example.healthconnect.db.PatientDAO;

import java.util.Calendar;

public class AddPatient extends AppCompatActivity {

    private EditText edtPhn, edtName, edtBirthday, edtAddress, edtPhone, edtPractitionerId;
    private RadioGroup radioGroupGender;
    private RadioButton radioMale, radioFemale;
    private Button btnSavePatient, btnBack, btnCancel;
    private PatientDAO patientDAO;
    int doctorID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_patient);

        doctorID = getIntent().getIntExtra("doctorID", 1);

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
                startActivity(new Intent(AddPatient.this, PatientManagement.class));
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddPatient.this, PatientManagement.class));
            }
        });

        edtBirthday.setOnClickListener(v -> {
            showDatePickerDialog();
        });
    }

    private void showDatePickerDialog(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Create and show a DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (DatePicker view, int selectedYear, int selectedMonth, int selectedDay) -> {
                    // Update the EditText with the selected date
                    String selectedDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                    edtBirthday.setText(selectedDate);
                },
                year, month, day
        );

        datePickerDialog.show();
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

        if(phnStr.length() != 9){
            Toast.makeText(this, "Please enter a valid personal health number", Toast.LENGTH_SHORT).show();
            return;
        }

        if(phnStr.isEmpty() || name.isEmpty() || birthday.isEmpty() || address.isEmpty() || phone.isEmpty() || practitionerIdStr.isEmpty() || gender.isEmpty()){
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        int phn = Integer.parseInt(phnStr);
        int practitionerId = doctorID;

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