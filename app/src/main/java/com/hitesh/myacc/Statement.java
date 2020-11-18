package com.hitesh.myacc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class Statement extends AppCompatActivity {

    Context context;
    List<Transaction> transaction = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statements);
        context = this;
        String month = getIntent().getStringExtra("month");
        getTransactions(month);
        RecyclerForTransactions recyclerForTransactions = new RecyclerForTransactions(context, transaction);
        RecyclerView recyclerView = findViewById(R.id.transactions);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(recyclerForTransactions);
    }

    private void getTransactions(String month) {
        SQLiteDatabase db = MainActivity.sqLiteHelper.getReadableDatabase();
        Cursor cursor = db.query("'" + month + "'", null, null, null, null, null, null);
        if(cursor != null) {
            if(cursor.moveToLast()) {
                do {
                    int day = cursor.getInt(1);
                    String p = cursor.getString(2);
                    float amt = cursor.getFloat(3);
                    boolean recvd = false;
                    if (amt == 0) {
                        recvd = true;
                        amt = cursor.getFloat(4);
                    }
                    transaction.add(new Transaction(p, day, recvd, amt));
                } while (cursor.moveToPrevious());
            }
            cursor.close();
        }
    }

    public static class Transaction {
        String particular;
        int day;
        boolean received;
        float amount;

        public Transaction(String particular, int day, boolean received, float amount) {
            this.particular = particular;
            this.day = day;
            this.received = received;
            this.amount = amount;
        }
    }
}