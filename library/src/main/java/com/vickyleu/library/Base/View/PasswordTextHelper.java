package com.vickyleu.library.Base.View;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by Administrator on 2016/5/26.
 */
public class PasswordTextHelper implements TextWatcher {
    DrawablePassword editText;

    public PasswordTextHelper(DrawablePassword editText) {
        this.editText = editText;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        editText.RefreshDrawable(s, start, count, this);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
