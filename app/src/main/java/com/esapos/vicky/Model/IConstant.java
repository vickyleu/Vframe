package com.esapos.vicky.Model;

import android.os.Handler;

import java.util.concurrent.Delayed;


public class IConstant {

    static Handler handler=new Handler();

    public static void handler(Runnable r,int delayed) {
        handler.postDelayed(r,delayed);
    }
}
