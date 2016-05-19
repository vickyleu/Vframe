package com.esapos.vicky.View;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vickyleu.library.Base.View.BaseFragment;

/**
 * @Created by VickyLeu on 2016/5/8.
 * @Author VickyLeu
 * @Companny Esapos
 * ===================================================*
 * 　　　　　　　　　　_ooOoo_
 * 　　　　　　　　　　o8888888o
 * 　　　　　　　　　　88" . "88
 * 　　　　　　　　　　(| -_- |)
 * 　　　　　　　　　　O\  =  /O
 * 　　　　　　　　____/`---'\____
 * 　　　　　　　　.'  \\|     |//  `.
 * 　　　　　　　/  \\|||  :  |||//  \
 * 　　　　　　/  _||||| -:- |||||-  \
 * 　　　　　　|   | \\\  -  /// |   |
 * 　　　　　　| \_|  ''\---/''  |   |
 * 　　　　　　\  .-\__  `-`  ___/-. /
 * 　　　　　　___`. .'  /--.--\  `. . __
 * 　　　　　"" '<  `.___\_<|>_/___.'  >'"".
 * 　　　| | :  `- \`.;`\ _ /`;.`/ - ` : | |
 * 　　　\  \ `-.   \_ __\ /__ _/   .-` /  /
 * ======`-.____`-.___\_____/___.-`____.-'======
 * 　　　　　　　　　　`=---='
 * ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 * 　　　　　　佛祖保佑　　　　永无BUG
 * <p/>
 * =================================================*
 */
public class LoginFragment extends BaseFragment {

    public LoginFragment() {
    }

    @SuppressLint("ValidFragment")
    public LoginFragment(Complated complated, int layout) {
        super(complated, layout);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Log.e("onCreateView", "onCreateView: ");
        return super.onCreateView(inflater, container, savedInstanceState);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e("onViewCreated", "onViewCreated" + view);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("onViewCreated", "onResume");
    }


    @Override
    public void onStop() {
        super.onStop();
    }


    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }
}
