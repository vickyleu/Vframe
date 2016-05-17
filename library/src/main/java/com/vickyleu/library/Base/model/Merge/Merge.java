package com.vickyleu.library.Base.model.Merge;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.vickyleu.library.Base.Controller.BaseRecyclerAdapter;
import com.vickyleu.library.Base.Presenter.IPresenter;
import com.vickyleu.library.Base.View.BaseActivity;


public class Merge<BASE extends BaseActivity, D extends BaseRecyclerAdapter, P extends IPresenter> implements IView, IP_C {


    D d;
    BASE v;
    P p;


    public Merge(BASE v, P p) {
        this.v = v;
        this.p = p;
        registerActivity(this.v);
        registerIP_C(this.p);
    }

    public Merge(BASE v, D d) {
        this.v = v;
        this.d = d;
        registerActivity(this.v);
        registerIcontroller(this.d);
    }

    private void registerIcontroller(D d) {
        d.registerIView(this);
    }

    private void registerIP_C(P p) {
        p.registerIView(this);
    }

    private void registerActivity(BASE v) {
        v.registerIP_C(this);
    }

    @Override
    public void notifyIP_C(View view, int resId, int layout) {
        if (p != null) {
            p.receiverNotify(view, resId, layout);
        }
    }


    @Override
    public void notifyView(String mTag, View view, int layout) {
        if (v != null) {
            v.receiverNotify(mTag, view, layout);
        }
    }

    @Override
    public <VH extends RecyclerView.ViewHolder, D> void notifyView(int position, VH vh, D d, int resId, int layout) {
        if (this.d != null) {
            this.d.receiverNotify(position, vh, d, resId, layout);
        }
    }
}
