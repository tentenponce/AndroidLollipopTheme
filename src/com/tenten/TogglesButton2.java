package com.tenten;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class TogglesButton2 extends LinearLayout {

    GestureDetector mgestureDetector;
    Context c;

    public TogglesButton2(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        c = context;
        mgestureDetector = new GestureDetector(c, new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onDown(MotionEvent e){
                Intent i = new Intent("boompaw");
                c.sendBroadcast(i);
                return true;
            }
        });
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mgestureDetector.onTouchEvent(event);
        return true;
    }
}
