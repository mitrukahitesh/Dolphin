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

public class Months extends AppCompatActivity {

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_months);
        context = this;
        List<String> months = getMonths();
        RecyclerForMonths recyclerForMonths = new RecyclerForMonths(context, months);
        RecyclerView recyclerView = findViewById(R.id.months);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(recyclerForMonths);
    }

    private List<String> getMonths() {
        List<String> months = new ArrayList<>();
        SQLiteDatabase db = MainActivity.sqLiteHelper.getReadableDatabase();
        Cursor cursor = db.query("Months", null, null, null, null, null, null);
        if(cursor != null) {
            if(cursor.moveToLast()) {
                do {
                    String month = cursor.getString(1);
                    months.add(month);
                }while (cursor.moveToPrevious());
            }
            cursor.close();
        }
        return months;
    }
}