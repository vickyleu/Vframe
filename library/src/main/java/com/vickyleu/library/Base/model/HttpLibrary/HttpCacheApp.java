package com.vickyleu.library.Base.model.HttpLibrary;



import android.app.Application;
import android.net.http.HttpResponseCache;
import android.os.Build;

import java.io.File;
import java.io.IOException;

public class HttpCacheApp extends Application {
    public HttpCacheApp() {
    }

    public void onCreate() {
        super.onCreate();
        File file = new File(this.getCacheDir(), "http");
        long size = 52428800L;

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                HttpResponseCache.install(file, size);
            }
        } catch (IOException var5) {
            var5.printStackTrace();
        }

    }
}

