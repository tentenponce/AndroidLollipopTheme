package com.tenten;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by pc on 7/9/14.
 */
public class UpDownBoomPaw extends LinearLayout {

    Context c;
    int MIN_DISTANCE = 100;
    float downX, downY, upX, upY;

    public UpDownBoomPaw(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        c = context;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN: {
                downX = event.getX();
                downY = event.getY();
                return true;
            }
            case MotionEvent.ACTION_UP: {
                upX = event.getX();
                upY = event.getY();

                float deltaX = downX - upX;
                float deltaY = downY - upY;

                // swipe vertical?
                if(Math.abs(deltaY) > MIN_DISTANCE){
                    // top or down
                    if(deltaY < 0) {
                        //this.onTopToBottomSwipe(v);
                        Intent i = new Intent("boompawnert");
                        c.sendBroadcast(i);
                        Intent i2 = new Intent("shitbrix");
                        c.sendBroadcast(i2);
                        return true;
                    }
                    if(deltaY > 0) {
                        //this.onBottomToTopSwipe(v);
                        Intent i = new Intent("boompawpaw");
                        c.sendBroadcast(i);
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
