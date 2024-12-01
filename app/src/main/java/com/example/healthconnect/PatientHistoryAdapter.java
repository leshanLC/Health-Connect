package com.example.healthconnect;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.example.healthconnect.datamodel.PatientHistory;
import com.example.healthconnect.db.PatientHistoryDAO;

import java.util.List;

public class PatientHistoryAdapter extends BaseAdapter {
    private Context context;
    private List<PatientHistory> historyList;
    Button btnHistoryEdit, btnHistoryDelete;

    public PatientHistoryAdapter(Context context, List<PatientHistory> historyList) {
        this.context = context;
        this.historyList = historyList;
    }

    @Override
    public int getCount() {
        return historyList.size();
    }

    @Override
    public Object getItem(int position) {
        return historyList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.patient_history_item, parent, false);
        }

        PatientHistory history = historyList.get(position);

        TextView txtDateTime = convertView.findViewById(R.id.txtDateTime);
        TextView txtWeight = convertView.findViewById(R.id.txtWeight);
        TextView txtHeight = convertView.findViewById(R.id.txtHeight);
        TextView txtDiagnoses = convertView.findViewById(R.id.txtDiagnoses);
        TextView txtTreatments = convertView.findViewById(R.id.txtTreatments);
        Button btnViewMedi = convertView.findViewById(R.id.btnViewMedi);

        txtDateTime.setText("Date/Time1: " + history.getDateTime());
        txtWeight.setText("Weight: " + history.getWeight() + " kg");
        txtHeight.setText("Height: " + history.getHeight() + " cm");
        txtDiagnoses.setText("Diagnoses: " + history.getDiagnoses());
        txtTreatments.setText("Treatments: " + history.getTreatments());

        btnViewMedi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Medication.class);
                intent.putExtra("historyID",history.getHistoryId());
                context.startActivity(intent);
            }
        });

        return convertView;
    }
}
