package com.vickyleu.library.Base.View.CallBack;

import android.content.DialogInterface;


public  interface Dlgbutton {
    void button_positive(DialogInterface dialog);

    void button_negative(DialogInterface dialog);

    void button_neutral(DialogInterface dialog);
}
