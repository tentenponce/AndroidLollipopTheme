package com.tenten;

import android.app.StatusBarManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.provider.Settings;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

public class LAutoRotateToggle extends ImageView {
    public LAutoRotateToggle(final Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

        Handler mHandler = new Handler();
        SettingsObserver settingsObserver = new SettingsObserver(mHandler);
        settingsObserver.observe();
        updateIcon(context);
        setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent i = new Intent("android.intent.action.MAIN");
                i.setComponent(new ComponentName("com.android.settings", "com.android.settings.DisplaySettings"));
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                v.getContext().startActivity(i);
                final StatusBarManager statusBar = (StatusBarManager) context.getSystemService(context.STATUS_BAR_SERVICE);
                if (statusBar != null) {
                    statusBar.collapse();
                }
                return true;
            }
        });

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
                setAutoOrientationEnabled(context);
                updateIcon(context);
            }
        });

    }

    private void updateIcon(Context c) {
        if(android.provider.Settings.System.getInt(c.getContentResolver(),Settings.System.ACCELEROMETER_ROTATION, 0) == 1){
            setImageDrawable(setDrawable("quickpanel_icon_rotation_on"));
        }else{
            setImageDrawable(setDrawable("quickpanel_icon_rotation_off"));
        }
    }

    public void setAutoOrientationEnabled(Context c)
    {
        if(android.provider.Settings.System.getInt(c.getContentResolver(),Settings.System.ACCELEROMETER_ROTATION, 0) == 1){
            android.provider.Settings.System.putInt(c.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, 0);
        }else{
            android.provider.Settings.System.putInt(c.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, 1);
        }
    }

    class SettingsObserver extends ContentObserver {
        SettingsObserver(Handler handler) {
            super(handler);
        }

        void observe() {
            ContentResolver resolver = mContext.getContentResolver();
            resolver.registerContentObserver(Settings.System.getUriFor(
                    Settings.System.ACCELEROMETER_ROTATION), false, this);
        }

        public void onChange(boolean selfChange) {
            updateIcon(mContext);
        }
    }

    public Drawable setDrawable(String mDrawableName){
        final String packName = "com.android.systemui";
        int mDrawableResID = 0 ;
        Drawable myDrawable = null;
        try {
            PackageManager manager = getContext().getPackageManager();
            Resources mApk1Resources = manager.getResourcesForApplication(packName);

            mDrawableResID = mApk1Resources.getIdentifier(mDrawableName, "drawable", packName);

            myDrawable = mApk1Resources.getDrawable( mDrawableResID );
        }
        catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return myDrawable;
    }
}
