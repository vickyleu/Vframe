package com.vickyleu.library.Base.model.Merge;

import com.vickyleu.library.Base.Controller.BaseRecyclerAdapter;
import com.vickyleu.library.Base.Presenter.IPresenter;
import com.vickyleu.library.Base.View.BaseActivity;


public class MergeUtils<BASE extends BaseActivity, D extends BaseRecyclerAdapter, P extends IPresenter> {
    private Merge view2Controller;
    private Merge view2Presenter;

    public MergeUtils(BASE base, D d) {
        view2Controller = new Merge(base, d);

    }

    public MergeUtils(BASE base, P p) {
        view2Presenter = new Merge(base, p);
    }
}
