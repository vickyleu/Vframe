package com.vickyleu.library.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.vickyleu.library.Base.model.AppCenter;
import com.vickyleu.library.Base.model.DbCallBack;
import com.vickyleu.library.R;


public class DataBaseCenter extends SQLiteOpenHelper {
    private static DataBaseCenter helper = null;
    private static String mName = "";
    private static int mVersion = -1;
    private static DbCallBack mCall;

    protected DataBaseCenter(Context context, String name, int version) {
        super(context, name, null, version);
    }

    public static void init(AppCenter app, String name, int version) {
        mCall = app.getCallback();
        mName = name;
        mVersion = version;
    }

    public static synchronized DataBaseCenter getDBInstance(Context mContext) throws Exception {
        if (mName == null || mName.equals("") || mVersion == -1 || mCall == null)
            throw new Exception(mContext.getString(R.string.database_init_error));
        if (helper == null) {
            helper = new DataBaseCenter(mContext, mName, mVersion);
        }
        return helper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        mCall.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        mCall.onUpgrade(db, oldVersion, newVersion);
    }
}
