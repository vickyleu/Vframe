package com.vickyleu.library.Base.Controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * Created by Administrator on 2016/5/24.
 */
public class BaseReceiver extends BroadcastReceiver {
    String[] action;
    Context context;
    String name;
    Receiver receiver;
    private boolean _reg = false;

    public static void install(Context context, String name, Receiver receiver, String... action) {
        new BaseReceiver(context, name, receiver, action);
    }

    private BaseReceiver(Context context, String name, Receiver receiver, String... action) {
        this.action = action;
        this.context = context;
        this.receiver = receiver;
        this.name = name;
        if (!_reg)
            register();
    }

    private void register() {
        if (action == null || action.length == 0) return;
        IntentFilter filter = new IntentFilter();
        for (int i = 0; i < action.length; i++) {
            filter.addAction(action[i]);
        }
        _reg = true;
        context.registerReceiver(this, filter);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (receiver != null)
            receiver.onReceiverWorking(name, intent.getAction(),context, intent);
    }

    public final void unregister() {
        if (_reg) context.unregisterReceiver(this);
    }

    public static interface Receiver {
        void onReceiverWorking(String name,String action, Context context, Intent intent);
    }
}
