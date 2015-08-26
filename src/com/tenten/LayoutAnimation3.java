package com.tenten;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;

public class LayoutAnimation3 extends LinearLayout {

    Animation fadeIn;
    Context c;
    AttributeSet at;

    public LayoutAnimation3(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        c = context;
        at = attributeSet;

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
                boompawpaw();
            }
        }, new IntentFilter("shitbrix"));
    }

    public void boompawpaw() {
        if((new LayoutAnimation1(c, at).getVisibility() == VISIBLE)||(new LayoutAnimation2(c, at).getVisibility() == VISIBLE)){
            boompaw();
        }
    }

    public void setAnimations() {
        fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
        fadeIn.setDuration(500);
    }

    public void boompaw() {
        setVisibility(GONE);
        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                setVisibility(VISIBLE);
                startAnimation(fadeIn);
            }
        }, 450);
    }
}
