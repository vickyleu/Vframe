package com.vickyleu.library.Base.Controller.CallBack;

import android.view.View;


public interface OnItemClickListener<T> {
    void onItemClick(OnItemCallBack requestAdapter, View view, T item, int position);
}
