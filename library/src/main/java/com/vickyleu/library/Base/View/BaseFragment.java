package com.vickyleu.library.Base.View;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public abstract class BaseFragment extends Fragment {

    private Complated complated;
    private int layout;

    public BaseFragment() {
    }

    @SuppressLint("ValidFragment")
    public BaseFragment(Complated complated, int layout) {
        this.complated = complated;
        this.layout = layout;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        complated.onFragmentViewCreated(view, layout);
    }

    public interface Complated {
        void onFragmentViewCreated(View view, int layout);
    }
}
