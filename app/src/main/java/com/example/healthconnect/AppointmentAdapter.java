package com.example.healthconnect;

import android.app.AlertDialog;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthconnect.datamodel.Appointment;
import com.example.healthconnect.db.AppointmentDAO;

import java.util.List;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.ViewHolder> {

    private final List<Appointment> appointments;

    public AppointmentAdapter(List<com.example.healthconnect.datamodel.Appointment> appointments) {
        this.appointments = appointments;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtID, txtPhoneNumber, txtDateTime, txtReason;
        ImageButton btnView, btnDelete, btnUpdate;
        Button btnSendEmail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtID = itemView.findViewById(R.id.txtAppointmentID);
            txtPhoneNumber = itemView.findViewById(R.id.txtPatientPhoneNumber);
            txtDateTime = itemView.findViewById(R.id.txtAppointmentDate);
            txtReason = itemView.findViewById(R.id.txtAppointmentReason);

            btnView = itemView.findViewById(R.id.btnViewAppointment);
            btnUpdate = itemView.findViewById(R.id.btnUpdateAppointment);
            btnDelete = itemView.findViewById(R.id.btnDeleteAppointment);
            btnSendEmail = itemView.findViewById(R.id.btnSendEmail);
        }
    }


    @NonNull
    @Override
    public AppointmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.appointment_list, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentAdapter.ViewHolder holder, int position) {
        com.example.healthconnect.datamodel.Appointment appointment = appointments.get(position);
        holder.txtID.setText(String.valueOf(String.valueOf(appointment.getId())));
        holder.txtPhoneNumber.setText(String.valueOf(appointment.getPatientPhn()));
        holder.txtDateTime.setText(appointment.getDateTime());
        holder.txtReason.setText(appointment.getReason());

        holder.btnView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), ViewAppointment.class);
            intent.putExtra("appointmentId", appointment.getId());
            v.getContext().startActivity(intent);
        });

        holder.btnUpdate.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), UpdateAppointment.class);
            intent.putExtra("appointmentId", appointment.getId());
            v.getContext().startActivity(intent);
        });

        holder.btnDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(v.getContext()).setTitle("Delete Appointment").setMessage("Are you sure you want to delete this Appointment?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        AppointmentDAO appointmentDAO = new AppointmentDAO(v.getContext());
                        appointmentDAO.deleteAppointment(appointment.getId());
                        appointments.remove(appointment);
                        notifyItemRemoved(position);

                    }).setNegativeButton("No", null).show();
        });

        holder.btnSendEmail.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), EmailReminder.class);
            intent.putExtra("appointmentId", appointment.getId());
            v.getContext().startActivity(intent);
            Log.d("Button Click", "Button clicked!");
        });

    }

    @Override
    public int getItemCount() {
        return appointments.size();
    }
}
