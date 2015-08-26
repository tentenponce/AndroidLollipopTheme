package com.tenten;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;

public class LayoutAnimation2 extends LinearLayout {

    Animation enter,exit;
    Context c;

    public LayoutAnimation2(Context context, AttributeSet attributeSet) {
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
        if(getVisibility() == VISIBLE){
            startAnimation(exit);
            Handler h = new Handler();
            h.postDelayed(new Runnable() {
                @Override
                public void run() {
                    setVisibility(GONE);
                }
            }, 150);
        }
    }

    public void boompawnert(){
        if(getVisibility() == GONE) {
            Handler h = new Handler();
            h.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startAnimation(enter);
                    setVisibility(VISIBLE);
                }
            }, 300);
        }
    }

    public void setAnimations() {
        enter = new ScaleAnimation(1f,1f,0f,1f,.5f,.2f);
        exit = new ScaleAnimation(1f,1f,1f,0f,.5f,.2f);
        enter.setDuration(150);
        exit.setDuration(150);
        enter.setInterpolator(new AccelerateInterpolator());
        exit.setInterpolator(new AccelerateInterpolator());
    }

    public void boompaw() {
        if(getVisibility() == VISIBLE){
            startAnimation(exit);
            Handler h = new Handler();
            h.postDelayed(new Runnable() {
                @Override
                public void run() {
                    setVisibility(GONE);
                }
            }, 150);
        }else if(getVisibility() == GONE){
            Handler h = new Handler();
            h.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startAnimation(enter);
                    setVisibility(VISIBLE);
                }
            }, 300);
        }
    }
}
