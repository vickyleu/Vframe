package com.vickyleu.library.Base.model;

import android.view.View;


public interface IViewModel<T extends View> {
    T getView();

    void setView(T t);
}
