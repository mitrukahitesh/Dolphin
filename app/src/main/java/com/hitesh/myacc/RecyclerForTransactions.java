package com.hitesh.myacc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;

public class RecyclerForTransactions extends RecyclerView.Adapter<RecyclerForTransactions.CustomHolder> {

    Context context;
    List<Statement.Transaction> transactions;

    public RecyclerForTransactions(Context context, List<Statement.Transaction> transactions) {
        this.context = context;
        this.transactions = transactions;
    }

    @NonNull
    @Override
    public CustomHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transview, parent, false);
        return new CustomHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomHolder holder, int position) {
        holder.day.setText(String.format(Locale.getDefault(),"%d", transactions.get(position).day));
        holder.particular.setText(transactions.get(position).particular);
        String amt = Float.toString((transactions.get(position).amount));
        holder.amount.setText(amt);
        if(transactions.get(position).received) {
            holder.amount.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.drawable.plus), null, null, null);
        }
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public class CustomHolder extends RecyclerView.ViewHolder {

        TextView day, particular, amount;

        public CustomHolder(@NonNull View itemView) {
            super(itemView);
            day = itemView.findViewById(R.id.day);
            particular = itemView.findViewById(R.id.particular);
            amount = itemView.findViewById(R.id.amount);
        }
    }
}
