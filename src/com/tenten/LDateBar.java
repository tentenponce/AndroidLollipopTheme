package com.tenten;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.AttributeSet;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class LDateBar extends TextView {
    public LDateBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        DateFormat df = new SimpleDateFormat("EEE, MMM d");
        String date = df.format(Calendar.getInstance().getTime());
        setText(date);
        invalidate();

        IntentFilter intentfilter = new IntentFilter();
        intentfilter.addAction(Intent.ACTION_TIME_TICK);
        intentfilter.addAction(Intent.ACTION_TIME_CHANGED);
        intentfilter.addAction(Intent.ACTION_TIMEZONE_CHANGED);
        intentfilter.addAction(Intent.ACTION_CONFIGURATION_CHANGED);
        context.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                DateFormat df = new SimpleDateFormat("EEE, MMM d");
                String date = df.format(Calendar.getInstance().getTime());
                setText(date);
                invalidate();
            }
        }, intentfilter);
    }
}
