package com.tenten;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

public class TogglesButton extends LinearLayout {

    Context c;

    public TogglesButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        c = context;

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent("boompaw");
                c.sendBroadcast(i);
            }
        });
    }
}
