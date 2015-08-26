package com.tenten;

import android.app.StatusBarManager;
import android.content.*;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

public class LSettingsBar extends ImageView {
    public LSettingsBar(final Context context, final AttributeSet attributeSet) {
        super(context, attributeSet);

        setImageDrawable(setDrawable("ic_launcher_settings"));

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent("android.intent.action.MAIN");
                i.setComponent(new ComponentName("com.android.settings", "com.android.settings.Settings"));
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                v.getContext().startActivity(i);
                final StatusBarManager statusBar = (StatusBarManager) context.getSystemService(context.STATUS_BAR_SERVICE);
                if (statusBar != null) {
                    statusBar.collapse();
                }
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
