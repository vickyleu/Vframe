package com.vickyleu.library.Model;

import android.content.Context;

import com.vickyleu.library.Base.model.AppCenter;
import com.vickyleu.library.R;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitEngine {
    private static final int DEFAULT_TIMEOUT = 6;
    private Retrofit retrofit;
    private static RetrofitEngine singleton;

    //构造私有方法
    private RetrofitEngine(Context context) {
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        AppCenter app = null;
        if (context != null) app = (AppCenter) context.getApplicationContext();
        if (app == null)
            throw new RuntimeException(AppCenter.getInstant().getString(R.string.host_unused));
        retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(app.getBaseUrl())
                .build();
    }

    //获取单例
    public synchronized static RetrofitEngine getInstance(Context context) {
        if (singleton == null) {
            singleton = new RetrofitEngine(context);
        }
        return singleton;
    }

    public final <T> T initService(Class<T> t) {
        if (retrofit == null)
            throw new RuntimeException(AppCenter.getInstant().getString(R.string.init_net_error));
        return retrofit.create(t);
    }
}
