package com.tenten;

import android.app.StatusBarManager;
import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothHeadset;
import android.bluetooth.BluetoothPbap;
import android.content.*;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

public class LBluetoothToggle extends ImageView {
    BluetoothAdapter bluetoothAdapter;
    public LBluetoothToggle(final Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        IntentFilter iF = new IntentFilter();
        iF.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        iF.addAction(BluetoothHeadset.ACTION_STATE_CHANGED);
        iF.addAction(BluetoothA2dp.ACTION_SINK_STATE_CHANGED);
        iF.addAction(BluetoothPbap.PBAP_STATE_CHANGED_ACTION);

        updateBluetoothIcon();

        context.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                updateBluetoothIcon();
            }
        }, iF);
        setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent i = new Intent("android.intent.action.MAIN");
                i.setComponent(new ComponentName("com.android.settings", "com.android.settings.bluetooth.BluetoothSettings"));
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

                boolean isEnabled = bluetoothAdapter.isEnabled();
                if(isEnabled){
                    bluetoothAdapter.disable();
                }else{
                    bluetoothAdapter.enable();
                }
                updateBluetoothIcon();
            }
        });
    }

    private void updateBluetoothIcon() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        boolean isEnabled = bluetoothAdapter.isEnabled();
        if(isEnabled){
            setImageDrawable(setDrawable("quickpanel_icon_bluetooth_on"));
        }else{
            setImageDrawable(setDrawable("quickpanel_icon_bluetooth_off"));
        }
    }

    public Drawable setDrawable(String mDrawableName){
        final String packName = "com.android.systemui";
        int mDrawableResID = 0;
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
