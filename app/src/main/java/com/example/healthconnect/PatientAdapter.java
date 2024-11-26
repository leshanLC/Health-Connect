package com.example.healthconnect;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthconnect.datamodel.Patient;
import com.example.healthconnect.db.PatientDAO;

import java.util.List;

public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.ViewHolder> {

    private Context context;
    private List<Patient> patients;
    private PatientDAO patientDAO;

    public PatientAdapter(Context context, List<Patient> patients, PatientDAO patientDAO){
        this.context = context;
        this.patients = patients;
        this.patientDAO = patientDAO;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.patient_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Patient patient = patients.get(position);
        holder.tvPatientName.setText(patient.getName());

        //View
        holder.btnView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ViewPatient.class);
            intent.putExtra("phn", patient.getPhn());
            context.startActivity(intent);
        });

        //Update
        holder.btnUpdate.setOnClickListener(v -> {
            Intent intent = new Intent(context, UpdatePatient.class);
            intent.putExtra("phn", patient.getPhn());
            context.startActivity(intent);
        });

        //Delete
        holder.btnDelete.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Confirm Deletion");
            builder.setMessage("Are you sure you want to delete this patient?");

            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    patientDAO.deletePatient(patient.getPhn());
                    patients.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, patients.size());
                }
            });

            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Dismiss the dialog
                    dialog.dismiss();
                }
            });

            builder.create().show();

        });

    }

    @Override
    public int getItemCount() {
        return patients.size();
    }

    public void updatePatients(List<Patient> newPatients) {
        this.patients = newPatients;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvPatientName;
        Button btnView, btnUpdate, btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPatientName = itemView.findViewById(R.id.tvPatientName);
            btnView = itemView.findViewById(R.id.btnView);
            btnUpdate = itemView.findViewById(R.id.btnUpdate);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
