package com.vickyleu.library.Base.model.Merge;

import com.vickyleu.library.Base.Controller.BaseRecyclerAdapter;
import com.vickyleu.library.Base.Presenter.IPresenter;
import com.vickyleu.library.Base.View.BaseActivity;

import java.lang.ref.WeakReference;


public class MergeUtils<BASE extends BaseActivity, D extends BaseRecyclerAdapter, P extends IPresenter> {
    private WeakReference<Merge> view2Controller;
    private WeakReference<Merge> view2Presenter;

    public MergeUtils(BASE base, D d) {
        view2Controller = new WeakReference<Merge>(new Merge(base, d));
    }

    public MergeUtils(BASE base, P p) {
        view2Presenter = new WeakReference<Merge>(new Merge(base, p));
    }
}
