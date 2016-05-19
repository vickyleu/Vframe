package com.esapos.vicky.Presenter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.esapos.vicky.R;
import com.esapos.vicky.View.LoginActivity;
import com.esapos.vicky.View.LoginFragment;
import com.vickyleu.library.Base.Presenter.IPresenter;


public class FragmentPresenter extends IPresenter<FrameLayout, FragmentTransaction> implements LoginFragment.Complated {
    private FragmentTransaction transaction;
    private String forceTag;
    private String oldTag;

    public FragmentPresenter(LoginActivity mAct, Context mContext, String mTag, FragmentTransaction transaction, FrameLayout frameLayout) {
        super(mAct, mContext, mTag, transaction, frameLayout);
    }

    @Override
    protected void onCreateView(LayoutInflater layoutInflater, FragmentTransaction transaction, FrameLayout frameLayout) {
        if (this.transaction == null) this.transaction = transaction;
    }

    @Override
    protected void onCreateView(FragmentTransaction transaction, FrameLayout frameLayout) {
        Log.e("forceTag", "onCreateView");
        if (this.transaction == null) this.transaction = transaction;
        LoginFragment f1 = new LoginFragment(this, R.layout.input_us_pw);
        setTag("login");
        switchFragments(this.transaction);
        beginTransaction(f1);
        transaction.commit();
    }

    private void setTag(String tag) {
        oldTag = forceTag;
        forceTag = tag;
    }

    @Override
    public void receiverNotify(View view, int i, int i1) {

    }

    @Override
    public void onFragmentViewCreated(View view, int layout) {
        refreshView(view, layout);
    }

    public void beginTransaction(LoginFragment fragment) {
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onRestart() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onDestroy() {

    }

    public void next(FragmentManager manager, String tag, int layout) throws Exception {
        transaction = manager.beginTransaction();
        switchFragments(transaction);
        LoginFragment ff = new LoginFragment(this, layout);
        beginTransaction(ff);
        transaction.commit();
        setTag(tag);
    }


    private void switchFragments(FragmentTransaction transaction) {
//        if (anim == FragmentAnim.HIDE) {
//           transaction = manager.beginTransaction();
//        transaction.setCustomAnimations(R.anim.push_right_in, R.anim.push_right_out, R.anim.push_right_in,
//                R.anim.push_right_out);
//            manager.popBackStack();
//        } else {


        transaction.setCustomAnimations(R.anim.push_left_in, R.anim.push_left_out);


//        , R.anim.push_left_in, R.anim.push_left_out);


//        }
    }

    public String getOldTag() {
        return oldTag;
    }

    public void show(String tag, FragmentManager manager) {
        try {
            transaction = manager.beginTransaction();
            LoginFragment fm = (LoginFragment) manager.findFragmentByTag(tag);
            if (fm != null) {
                transaction.show(fm);
            }
            oldTag = "";
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String getForceTag() {
        return forceTag;
    }

    enum FragmentAnim {
        SHOW,
        HIDE
    }
}
