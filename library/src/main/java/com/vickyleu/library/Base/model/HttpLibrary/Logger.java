package com.vickyleu.library.Base.model.HttpLibrary;


import android.util.Log;

public final class Logger {
    private static final String TAG = "com.vicky.http";
    public static boolean LOG = true;

    public Logger() {
    }

    public static final void log(Object o) {
        if(LOG) {
            if(o != null) {
                Log.v(TAG, o.toString());
            }

        }
    }
}

