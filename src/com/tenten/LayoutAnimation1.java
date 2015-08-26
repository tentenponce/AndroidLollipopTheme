package com.tenten;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.animation.*;
import android.widget.LinearLayout;

public class LayoutAnimation1 extends LinearLayout {

    Animation enter,exit;
    Context c;

    public LayoutAnimation1(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        c = context;

        setAnimations();

        c.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                boompaw();
            }
        }, new IntentFilter("boompaw"));
        c.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                boompawpaw();
            }
        }, new IntentFilter("boompawpaw"));
        c.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                 boompawnert();
            }
        }, new IntentFilter("boompawnert"));
    }

    public void boompawpaw(){
        if(getVisibility() == VISIBLE) {
            Handler h = new Handler();
            h.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startAnimation(exit);
                    Handler h = new Handler();
                    h.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            setVisibility(GONE);
                            Intent i = new Intent("labas1");
                            c.sendBroadcast(i);
                        }
                    }, 300);
                }
            }, 150);
        }
    }

    public void boompawnert(){
        if(getVisibility() == GONE){
            setVisibility(VISIBLE);
            startAnimation(enter);
        }
    }

    public void setAnimations() {
        enter = new ScaleAnimation(1f,1f,0f,1f,.5f,.2f);
        exit = new ScaleAnimation(1f,1f,1f,0f,.5f,.2f);
        enter.setDuration(300);
        exit.setDuration(300);
        enter.setInterpolator(new AccelerateInterpolator());
        exit.setInterpolator(new AccelerateInterpolator());
    }

    public void boompaw() {
        if(getVisibility() == VISIBLE){
            Handler h = new Handler();
            h.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startAnimation(exit);
                    Handler h = new Handler();
                    h.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            setVisibility(GONE);
                            Intent i = new Intent("labas1");
                            c.sendBroadcast(i);
                        }
                    }, 300);
                }
            }, 150);
        }else if(getVisibility() == GONE){
            setVisibility(VISIBLE);
            startAnimation(enter);
            Intent i = new Intent("tago1");
            c.sendBroadcast(i);
        }
    }
}
