package com.vickyleu.library.Base.Presenter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.vickyleu.library.Base.model.IDataModel;
import com.vickyleu.library.Base.model.IViewModel;
import com.vickyleu.library.Base.model.Merge.IP_C;



public abstract class IPresenter<T extends View, D> implements IViewModel<T>, IDataModel<D>, CycleCallBack {
    protected Activity mAct;
    public Context mContext;

    protected D f;
    protected T t;
    protected String mTag;
    protected String s;
    private IP_C ip_c;

    public IPresenter(Activity mAct, Context mContext, String mTag, D f, T t) {
        this.mAct = mAct;
        this.mTag = mTag;
        this.mContext = mContext;
        this.f = f;
        this.t = t;
        init();
    }


    public String getTag() {
        return mTag;
    }

    private void init() {
        onCreateView((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE), f, t);
        onCreateView(f, t);
    }

    protected void refreshView(View view, int layout) {
        refreshIView(this.getTag(), view, layout);
    }

    protected void refreshView(int resId, int layout) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        refreshIView(this.getTag(), inflater.inflate(layout, null).findViewById(resId), layout);
    }

    private final void refreshIView(String tag, View view, int layout) {
        if (ip_c != null)
            ip_c.notifyView(tag, view, layout);
    }

    protected abstract void onCreateView(LayoutInflater inflater, D d, T v);

    protected abstract void onCreateView(D d, T v);

    @Override
    public void setView(T t) {
        this.t = t;
    }

    @Override
    public void setData(D f) {
        this.f = f;
    }


    @Override
    public D getData() {
        return f;
    }

    @Override
    public T getView() {
        return t;
    }

    public CycleCallBack getCallBack() {
        return this;
    }

    public final void registerIView(IP_C ip_c) {
        this.ip_c = ip_c;
    }

    public abstract void receiverNotify(View view, int resId, int layout);

    @Override
    public void onDestroy() {

    }
}
