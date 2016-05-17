package com.vickyleu.library.Base.model.Merge;

import android.support.v7.widget.RecyclerView;
import android.view.View;


public interface IP_C {
    void notifyView(String mTag, View view, int layout);

    <VH extends RecyclerView.ViewHolder, D> void notifyView(int position, VH vh, D d, int resId, int layout);
}
