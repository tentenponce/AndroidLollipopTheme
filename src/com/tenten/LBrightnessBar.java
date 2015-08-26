package com.tenten;

import android.app.StatusBarManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

public class LBrightnessBar extends ImageView {
    public LBrightnessBar(final Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setBackgroundDrawable(setDrawable("ic_popup_brightness"));

        setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent i = new Intent(context, LFlashLight.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
                final StatusBarManager statusBar = (StatusBarManager) context.getSystemService(context.STATUS_BAR_SERVICE);
                if (statusBar != null) {
                    statusBar.collapse();
                }
                return true;
            }
        });

    }

    public Drawable setDrawable(String mDrawableName){
        final String packName = "com.android.settings";
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
