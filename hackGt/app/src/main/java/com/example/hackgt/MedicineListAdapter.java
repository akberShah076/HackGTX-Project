package com.example.hackgt;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MedicineListAdapter extends RecyclerView.Adapter<MedicineListAdapter.MedicineViewHolder> {
    private Context context;
    private List<Medicine> medicineList;

    public MedicineListAdapter(Context context, List<Medicine> medicineList) {
        this.context = context;
        this.medicineList = medicineList;
    }

    @NonNull
    @Override
    public MedicineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.medicine_list_item, parent, false);
        return new MedicineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicineViewHolder holder, int position) {
        Medicine medicine = medicineList.get(position);

        // Bind data to the views
        holder.medicineNameTextView.setText(medicine.getMedicineName());
        holder.dosageTextView.setText(medicine.getDosage());
        holder.frequencyTextView.setText(medicine.getFrequency());
        holder.treatmentNameTextView.setText(medicine.getTreatmentName());
    }

    @Override
    public int getItemCount() {
        return medicineList.size();
    }

    public class MedicineViewHolder extends RecyclerView.ViewHolder {
        TextView medicineNameTextView;
        TextView dosageTextView;
        TextView frequencyTextView;
        TextView treatmentNameTextView;

        public MedicineViewHolder(@NonNull View itemView) {
            super(itemView);
            medicineNameTextView = itemView.findViewById(R.id.medicineNameTextView);
            dosageTextView = itemView.findViewById(R.id.dosageTextView);
            frequencyTextView = itemView.findViewById(R.id.frequencyTextView);
            treatmentNameTextView = itemView.findViewById(R.id.treatmentNameTextView);
        }
    }
}