package com.vickyleu.library.Base.model;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.vickyleu.library.Base.model.Img.BaseImageCenter;
import com.vickyleu.library.Model.DataBaseCenter;
import com.vickyleu.library.Model.RetrofitEngine;
import com.vickyleu.library.Model.Rx;

import rx.Observable;
import rx.Subscriber;


public abstract class AppCenter extends Application implements DbCallBack, Rx {
    protected static AppCenter instant;
    private String name;
    private int version;
    private static String BASE_URL;

    protected abstract void initApp();

    protected abstract void initImgFromApp(BaseImageCenter center);

    protected abstract String setName();

    protected abstract int setVersion();


    BaseImageCenter center;


    @Override
    public void onCreate() {
        super.onCreate();
        instant = this;

        initApp();
        setNameAndVersion();
        loadImageCenter();
        loadDBCenter();

    }


    public static void setBaseUrl(String baseUrl) {
        BASE_URL = baseUrl;
    }

    public final String getBaseUrl() {
        return BASE_URL;
    }


    private void loadDBCenter() {
        if (name == null || name.equals("") || version == 0) {
            name = "app.db";
            version = 1;
        }
        initDB(name, version);
    }

    private void loadImageCenter() {
        center = setImgCenter();
        if (center == null) {
            center = new NormalImgCenter(this.getApplicationContext(), true);
        } else {
            initImgFromApp(center);
        }
    }


    protected abstract BaseImageCenter setImgCenter();


    protected void setNameAndVersion() {
        name = setName();
        version = setVersion();
    }


    protected void initDB(String name, int version) {
        DataBaseCenter.init(this, name, version);
    }

    public static synchronized AppCenter getInstant() {
        return instant;
    }

    public static boolean checkNet() {
        try {
            ConnectivityManager connectivity = (ConnectivityManager) getInstant()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo info = connectivity.getActiveNetworkInfo();
                if (info != null && info.isConnected()) {
                    if (info.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public final DbCallBack getCallback() {
        return this;
    }

    final class NormalImgCenter extends BaseImageCenter {

        public NormalImgCenter(Context context, boolean useNoraml) {
            super(context, useNoraml);
        }

        @Override
        protected ImageLoaderConfiguration getImageConfig() {
            return null;
        }

        @Override
        protected void CustomerOtherImgFramework() {

        }
    }

    public final <T> T buildRetrofit(Class<T> t) {
        return RetrofitEngine.getInstance(this).initService(t);
    }

    public <T> Observable<T> initObservable(T t) {
        return Observable.create((Observable.OnSubscribe<T>) (subscriber) -> {
            this.sendNext(subscriber, t);
        });
    }


    public static <T> Subscriber<T> initSubscriber(T t) {

        return new Subscriber<T>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(T t1) {
            }

        };
    }


    public <T extends Observable> T config(T t) {

        return t;
    }

    public final <T> void onCompleted(Subscriber<T> subscriber) {
        if (!subscriber.isUnsubscribed()) subscriber.onCompleted();
    }

    protected final <T> void onNext(Subscriber<T> subscriber, T d) {
        if (subscriber.isUnsubscribed()) return;
        subscriber.onNext(d);
    }

    public static <T> void Subscribe(Observable<T> observable, Subscriber<T> subscriber) {
        observable.subscribe(subscriber);
    }

}
