package com.example.healthconnect;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthconnect.datamodel.HistoryMedication;
import com.example.healthconnect.db.HistoryMedicationDAO;

import java.util.List;

public class MedicationAdapter extends RecyclerView.Adapter<MedicationAdapter.ViewHolder> {

    private final List<HistoryMedication> medications;

    public MedicationAdapter(List<HistoryMedication> medications) {
        this.medications = medications;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtMedId, txtMedHistory, txtMedicationName, txtMedicationForm, txtMedicationStrength, txtMedicationDosage, txtMedicationDuration;

        public ViewHolder(View itemView) {
            super(itemView);

            txtMedId = itemView.findViewById(R.id.txtMedID);
            txtMedHistory = itemView.findViewById(R.id.txtMedHistory);
            txtMedicationName = itemView.findViewById(R.id.txtMedicationName);
            txtMedicationForm = itemView.findViewById(R.id.txtMedicationForm);
            txtMedicationStrength = itemView.findViewById(R.id.txtMedicationStrength);
            txtMedicationDosage = itemView.findViewById(R.id.txtMedicationDosage);
            txtMedicationDuration = itemView.findViewById(R.id.txtMedicationDuration);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.medication_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HistoryMedication medication = medications.get(position);
        holder.txtMedId.setText(String.valueOf(medication.getMedId()));
        holder.txtMedHistory.setText(String.valueOf(medication.getHistoryId()));
        holder.txtMedicationName.setText(medication.getMedicineName());
        holder.txtMedicationForm.setText(medication.getForm());
        holder.txtMedicationStrength.setText(medication.getStrength());
        holder.txtMedicationDosage.setText(medication.getDosage());
        holder.txtMedicationDuration.setText(medication.getDuration());
    }

    @Override
    public int getItemCount() {
        return medications.size();
    }

}
