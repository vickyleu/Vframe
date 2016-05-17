package com.vickyleu.library.Base.View.CallBack;

import android.widget.EditText;

public  interface Wathcer {
    void before(EditText editText, String str, int res) throws Exception;

    void onChange(EditText editText, String str, int res) throws Exception;

    void after(EditText editText, String str, int res) throws Exception;
}
