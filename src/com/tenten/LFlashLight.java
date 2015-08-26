package com.tenten;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IPowerManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.provider.Settings;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class LFlashLight extends Activity {
    int mOldBrightness;
    private static final int MAXIMUM_BACKLIGHT = android.os.Power.BRIGHTNESS_ON;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ImageView white = new ImageView(this);
        white.setBackgroundColor(Color.WHITE);
        setBrightness(android.os.Power.BRIGHTNESS_ON);
        setContentView(white);
        try {
            mOldBrightness = Settings.System.getInt(getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS);
        } catch (Settings.SettingNotFoundException snfe) {
            mOldBrightness = MAXIMUM_BACKLIGHT;
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        setBrightness(mOldBrightness);
    }

    private void setBrightness(int brightness) {
        try {
            IPowerManager power = IPowerManager.Stub.asInterface(
                    ServiceManager.getService("power"));
            if (power != null) {
                power.setBacklightBrightness(brightness);
            }
        } catch (RemoteException doe) {

        }
    }
}
