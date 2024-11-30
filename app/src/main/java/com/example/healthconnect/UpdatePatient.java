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

public class UpdatePatient extends AppCompatActivity {

    EditText edtPhn, edtName, edtBirthday, edtAddress, edtPractitionerID, edtPhone;
    private RadioGroup radioGroupGender;
    private RadioButton radioMale, radioFemale;
    private PatientDAO patientDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_patient);

        Button btnUpdatePatient = (Button) findViewById(R.id.btnUpdatePatient);
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

        edtPhn.setKeyListener(null);

        patientDAO = new PatientDAO(this);

        int phn = getIntent().getIntExtra("phn", -1);
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
            }
        }

        btnUpdatePatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePatient();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UpdatePatient.this, PatientManagement.class));
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

    private void updatePatient() {
        String phnStr = edtPhn.getText().toString().trim();
        String name = edtName.getText().toString().trim();
        String birthday = edtBirthday.getText().toString().trim();
        String address = edtAddress.getText().toString().trim();
        String phone = edtPhone.getText().toString().trim();
        String practitionerIdStr = edtPractitionerID.getText().toString().trim();
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

        long result = patientDAO.updatePatient(patient);

        if(result != -1){
            Toast.makeText(this, "Patient Updated Successfully", Toast.LENGTH_SHORT).show();
            finish();
        }else{
            Toast.makeText(this, "Failed to Update the Patient", Toast.LENGTH_SHORT).show();
            edtPhn.setText("");
            edtName.setText("");
            edtBirthday.setText("");
            edtAddress.setText("");
            edtPhone.setText("");
            edtPractitionerID.setText("");
            radioGroupGender.clearCheck();
        }


    }
}