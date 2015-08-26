package com.tenten;

import android.app.StatusBarManager;
import android.content.*;
import android.os.Handler;
import android.provider.Settings;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

public class LAirplaneToggle extends ImageView {
    public LAirplaneToggle(final Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

        context.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                updateIcon(context);
            }
        }, new IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED));

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                final Animation fadeIn = new AlphaAnimation(0, 1);
                fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
                fadeIn.setDuration(500);

                final Animation fadeOut = new AlphaAnimation(1, 0);
                fadeOut.setInterpolator(new AccelerateInterpolator()); //and this
                fadeOut.setDuration(500);
                startAnimation(fadeOut);
                Handler h = new Handler();
                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startAnimation(fadeIn);
                    }
                }, 500);
                boolean isEnabled = Settings.System.getInt(context.getContentResolver(), Settings.System.AIRPLANE_MODE_ON, 0) == 1;
                Settings.System.putInt(context.getContentResolver(), Settings.System.AIRPLANE_MODE_ON, isEnabled ? 0 : 1);
                Intent intent = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);
                intent.putExtra("state", !isEnabled);
                context.sendBroadcast(intent);
                updateIcon(context);
            }
        });

        setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent i = new Intent("android.intent.action.MAIN");
                i.setComponent(new ComponentName("com.android.settings", "com.android.settings.WirelessSettings"));
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                view.getContext().startActivity(i);
                final StatusBarManager statusBar = (StatusBarManager) context.getSystemService(context.STATUS_BAR_SERVICE);
                if (statusBar != null) {
                    statusBar.collapse();
                }
                return true;
            }
        });

        updateIcon(context);
    }

    public void updateIcon(Context context){
        if(Settings.System.getInt(context.getContentResolver(),Settings.System.AIRPLANE_MODE_ON, 0) == 1){
            setImageResource(0x10801a9);
        }else{
            setImageResource(0x10801aa);
        }
    }
}
