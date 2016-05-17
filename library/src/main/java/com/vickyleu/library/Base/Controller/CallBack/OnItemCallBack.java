package com.vickyleu.library.Base.Controller.CallBack;

import android.view.View;


public interface OnItemCallBack<T> {
    void BaseItemClick(View view, T item, int position);
    void BaseItemLongClick(View view, T item, int position);
}
