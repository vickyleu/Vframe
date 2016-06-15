package com.vickyleu.library.Base.Native;

/**
 * Created by Administrator on 2016/6/13.
 */
public class Native {
    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    public native String stringFromJNI();
    public native int LibMain();
}
