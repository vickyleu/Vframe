package com.vickyleu.library.Base.model;

import android.database.sqlite.SQLiteDatabase;


public interface DbCallBack {
    void onCreate(SQLiteDatabase db);
    void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion);
}
