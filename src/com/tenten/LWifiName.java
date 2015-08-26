package com.tenten;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.TextView;


public class LWifiName extends TextView {
    GestureDetector mgestureDetector;
    public LWifiName(final Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        getWifiName(context);
        mgestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onDown(MotionEvent e){
                setText("Updating..");
                Handler h = new Handler();
                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getWifiName(context);

                    }
                }, 1000);
                return true;
            }
        });
        IntentFilter filter = new IntentFilter();

        filter.addAction(WifiManager.SUPPLICANT_CONNECTION_CHANGE_ACTION);
        context.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                getWifiName(context);
            }
        }, new IntentFilter("wifing"));

        context.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Intent i = new Intent("wifing");
                context.sendBroadcast(i);
            }
        }, filter);
    }

    public void getWifiName(Context context) {
        WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (manager.isWifiEnabled()) {
            final WifiInfo wifiInfo = manager.getConnectionInfo();
            if (wifiInfo != null) {
                setText("Connecting..");
                Handler h = new Handler();
                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setText(wifiInfo.getSSID());

                    }
                }, 3000);
            }else{
                setText("On");
            }
        }else{
            setText("Off");
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mgestureDetector.onTouchEvent(event);
        return true;
    }
}
