package com.example.d308_rhuerta_vacation_planner.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.d308_rhuerta_vacation_planner.R;
import com.example.d308_rhuerta_vacation_planner.entities.Excursion;

import java.util.List;

public class ExcursionAdapter extends RecyclerView.Adapter<ExcursionAdapter.ExcursionViewHolder> {
    private List<Excursion> mExcursions;
    private final Context context;
    private final LayoutInflater mInflater;
    class ExcursionViewHolder extends RecyclerView.ViewHolder {
        private final TextView excursionTextView3;
        private final TextView excursionTextView4;

        private ExcursionViewHolder(View itemView) {
            super(itemView);
            excursionTextView3 = itemView.findViewById(R.id.textView3);
            excursionTextView4 = itemView.findViewById(R.id.textView4);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final Excursion current = mExcursions.get(position);
                    Intent intent = new Intent(context, ExcursionDetails.class);
                    intent.putExtra("id", current.getExcursionId());
                    intent.putExtra("name", current.getExcursionName());
                    intent.putExtra("date", current.getDate());
                    context.startActivity(intent);
                }
            });
        }
    }
        public ExcursionAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
            this.context = context;
        }

        @NonNull
        @Override
        public ExcursionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = mInflater.inflate(R.layout.excursion_list_item, parent, false);
            return new ExcursionViewHolder(itemView);
        }
    @Override
    public void onBindViewHolder(@NonNull ExcursionViewHolder holder, int position) {
            if (mExcursions != null) {
                Excursion current = mExcursions.get(position);
                String name = current.getExcursionName();
                int vacID = current.getVacationId();
                holder.excursionTextView3.setText(name);
                holder.excursionTextView4.setText(Integer.toString(vacID));
            } else {
                holder.excursionTextView3.setText("No excursion name found!");
                holder.excursionTextView4.setText("No vacation ID found!");
            }
        }
    public void setExcursions(List<Excursion> excursions) {
            mExcursions = excursions;
            notifyDataSetChanged();
    }

    public int getItemCount() {
        if (mExcursions != null) {
            return mExcursions.size();
        } else {
            return 0;
        }
    }
}
