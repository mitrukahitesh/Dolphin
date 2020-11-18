package com.hitesh.myacc;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    Context context;
    ImageView thisMonth, history;
    Button received, paid;
    public static final String RECEIVED = "For / From";
    public static final String PAID = "For / To";
    public static final int VERSION = 1;
    public static CustomSQLiteHelper sqLiteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        thisMonth = findViewById(R.id.thismonth);
        history = findViewById(R.id.history);
        received = findViewById(R.id.received);
        paid = findViewById(R.id.paid);
        sqLiteHelper = new CustomSQLiteHelper(context, VERSION);
        addThisMonth();
        setListeners();
    }

    private void setListeners() {
        thisMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowsTransaction showsTransaction = new ShowsTransaction(context, getMonth());
                showsTransaction.show();
            }
        });
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Months.class);
                startActivity(intent);
            }
        });
        received.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData(true);
            }
        });
        paid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData(false);
            }
        });
    }

    private void insertData(final boolean b) {
        final View view = LayoutInflater.from(context).inflate(R.layout.datainput, null);
        if (b) {
            ((EditText) view.findViewById(R.id.against)).setHint(RECEIVED);
        } else {
            ((EditText) view.findViewById(R.id.against)).setHint(PAID);
        }
        final AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setCancelable(true)
                .setView(view)
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String particular = ((EditText) view.findViewById(R.id.against)).getText().toString();
                        float amt = Float.parseFloat(((EditText) view.findViewById(R.id.amt)).getText().toString());
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("particular", particular);
                        contentValues.put("day", getDay());
                        if (b) {        //true means received
                            contentValues.put("received", amt);
                        } else {
                            contentValues.put("paid", amt);
                        }
                        sqLiteHelper.getWritableDatabase().insert("'" + getMonth() + "'", null, contentValues);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //DO NOTHING
                    }
                })
                .create();
        alertDialog.show();
    }

    private void addThisMonth() {
        String month = getMonth();
        SQLiteDatabase db = sqLiteHelper.getReadableDatabase();
        Cursor cursor = db.query(CustomSQLiteHelper.MONTHS, null, "month = '" + month + "'", null, null, null, null, null);
        if(!cursor.moveToFirst()) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("month", month);
            db.close();
            db = sqLiteHelper.getWritableDatabase();
            db.execSQL("CREATE TABLE '" + month + "'(_id INTEGER PRIMARY KEY AUTOINCREMENT, day INTEGER, particular TEXT, paid REAL DEFAULT 0, received REAL DEFAULT 0)");
            db.insert(CustomSQLiteHelper.MONTHS, null, contentValues);
        }
        cursor.close();
        db.close();
    }

    public static String getMonth() {
        TimeZone zone = TimeZone.getDefault();
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy", Locale.ENGLISH);
        sdf.setTimeZone(zone);
        return sdf.format(date);
    }

    public static int getDay() {
        TimeZone zone = TimeZone.getDefault();
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("dd", Locale.ENGLISH);
        sdf.setTimeZone(zone);
        return Integer.parseInt(sdf.format(date));
    }
}