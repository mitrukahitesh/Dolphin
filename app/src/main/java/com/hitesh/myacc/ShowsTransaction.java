package com.hitesh.myacc;

import android.content.Context;
import android.content.Intent;

public class ShowsTransaction {
    Context context;
    String month;

    public ShowsTransaction(Context context, String month) {
        this.context = context;
        this.month = month;
    }

    public void show() {
        Intent intent = new Intent(context, Statement.class);
        intent.putExtra("month", month);
        context.startActivity(intent);
    }
}
