package com.vickyleu.library.Base.model;

import android.content.Context;
import android.content.Intent;

/**
 * Created by Administrator on 2016/5/24.
 */
public class BaseBroadcast {
    public static boolean send(Context context, String action) {
        try {
            Intent intent = new Intent();
            intent.setAction(action);
            context.sendBroadcast(intent);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
