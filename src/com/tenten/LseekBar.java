package com.tenten;

import android.content.Context;
import android.os.IPowerManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;
import android.util.AttributeSet;
import android.widget.SeekBar;

public class LseekBar extends SeekBar implements SeekBar.OnSeekBarChangeListener{

    private int mOldBrightness;
    private static final int MINIMUM_BACKLIGHT = android.os.Power.BRIGHTNESS_DIM + 10;
    private static final int MAXIMUM_BACKLIGHT = android.os.Power.BRIGHTNESS_ON;

    public LseekBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setMax(MAXIMUM_BACKLIGHT - MINIMUM_BACKLIGHT);
        try {
            mOldBrightness = Settings.System.getInt(getContext().getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS);
        } catch (SettingNotFoundException snfe) {
            mOldBrightness = MAXIMUM_BACKLIGHT;
        }

        setProgress(mOldBrightness - MINIMUM_BACKLIGHT);
        setOnSeekBarChangeListener(this);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {
        setBrightness(progress + MINIMUM_BACKLIGHT);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        Settings.System.putInt(getContext().getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS,
                getProgress() + MINIMUM_BACKLIGHT);

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
