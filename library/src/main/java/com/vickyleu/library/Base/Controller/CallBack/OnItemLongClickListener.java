package com.vickyleu.library.Base.Controller.CallBack;

import android.view.View;


public interface OnItemLongClickListener<T> {
    void onItemLongClick(OnItemCallBack requestAdapter, View view, T item, int position);
}
