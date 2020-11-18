package com.hitesh.myacc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerForMonths extends RecyclerView.Adapter<RecyclerForMonths.CustomHolder> {

    Context context;
    List<String> months;

    public RecyclerForMonths(Context context, List<String> months) {
        this.context = context;
        this.months = months;
    }

    @NonNull
    @Override
    public CustomHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.monthview, parent, false);
        return new CustomHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomHolder holder, int position) {
        holder.selectMonth.setText(months.get(position));
    }

    @Override
    public int getItemCount() {
        return months.size();
    }

    public class CustomHolder extends RecyclerView.ViewHolder {

        Button selectMonth;

        public CustomHolder(@NonNull View itemView) {
            super(itemView);
            selectMonth = itemView.findViewById(R.id.selectMonth);
            selectMonth.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShowsTransaction showsTransaction = new ShowsTransaction(context, months.get(getAdapterPosition()));
                    showsTransaction.show();
                }
            });
        }
    }
}
