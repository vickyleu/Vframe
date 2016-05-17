package com.vickyleu.library.Base.model;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * Created by Administrator on 2016/5/17.
 */
public abstract class IReceiver extends BroadcastReceiver {
    IntentFilter filter = null;
    private boolean flag = false;
    public static final String NET="android.net.conn.CONNECTIVITY_CHANGE";

    public void register(IntentFilter mFilter, Context mContext) {
        if (flag == false) {
            flag = true;
            mContext.registerReceiver(this, mFilter);
        }
    }

    public void unregister(Context mContext) {
        if (flag == true) {
            flag = false;
            mContext.unregisterReceiver(this);
            filter = null;
        }
    }


    @Override
    public void onReceive(Context context, Intent intent) {
    }

    public IntentFilter getFilter(String... action) {
        filter = new IntentFilter();
        for (int i = 0; i < action.length; i++) {
            filter.addAction(action[i]);
        }
        return filter;
    }




}
