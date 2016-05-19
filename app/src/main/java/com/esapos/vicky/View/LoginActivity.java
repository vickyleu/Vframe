package com.esapos.vicky.View;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.esapos.vicky.Presenter.FragmentPresenter;
import com.esapos.vicky.R;
import com.vickyleu.library.Base.View.BaseActivity;
import com.vickyleu.library.Base.model.HttpLibrary.HttpResponseModel;
import com.vickyleu.library.Base.model.Merge.MergeUtils;

public class LoginActivity extends BaseActivity {
    private Dialog loginDlg;
    private FragmentTransaction transaction;

    @Override
    public void createLayout() {
        layout(R.layout.activity_login, true);
    }

    @Override
    public void initView() {
        FrameLayout fl = toView(FrameLayout.class, R.id.container);
        Fragment();
        final FragmentPresenter presenter = new FragmentPresenter(this, this, "tag1", Fragment(), fl);
        setPresenter(presenter, R.id.container);
        new MergeUtils(this, presenter);
    }

    public FragmentTransaction Fragment() {
        if (transaction == null) {
            FragmentManager fragmentManager = getV4Manager();
            transaction = fragmentManager.beginTransaction();
        }
        return transaction;
    }

    @Override
    protected void onClickEvent(int i) {
        switch (i) {
            case R.id.submit:
                addReq("https://192.168.0.125", null, 2000);
                tips(R.string.login_tips, R.style.custom_dialog_base_style);
                break;
            case R.id.next_step:
                addReq("https://192.168.2.125", null, 2000);
                break;
            case R.id.reset_submit:
                addReq("https://192.168.6.125", null, 2000);
                tips(R.string.reset_success, R.style.custom_dialog_base_style);
                break;
        }
    }

    @Override
    public void httpErr(String s, HttpResponseModel model) {

        if (s.equals("https://192.168.0.125")) {
            submitResult(model);
        } else if (s.equals("https://192.168.2.125")) {
            nextStepResult(model);
        } else if (s.equals("https://192.168.6.125")) {
            resetSuccess(model);
        }

    }


    @Override
    public void httpSuccess(String s, HttpResponseModel model) {
        if (s.equals("https://192.168.0.125")) {
            submitResult(model);
        } else if (s.equals("https://192.168.2.125")) {
            nextStepResult(model);
        } else if (s.equals("https://192.168.6.125")) {
            resetSuccess(model);
        }

    }


    private void resetSuccess(HttpResponseModel model) {
        final FragmentPresenter presenter = (FragmentPresenter) getPresenter(R.id.container);
        try {
            presenter.next(getV4Manager(), "login", R.layout.input_us_pw);
        } catch (Exception e) {
            e.printStackTrace();
        }
        loginDlg.dismiss();
    }


    private void nextStepResult(HttpResponseModel model) {
        FragmentPresenter presenter = (FragmentPresenter) getPresenter(R.id.container);
        try {
            presenter.next(getV4Manager(), "reset", R.layout.reset_pw);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void submitResult(HttpResponseModel model) {
        final FragmentPresenter presenter = (FragmentPresenter) getPresenter(R.id.container);
        try {
            presenter.next(getV4Manager(), "phone", R.layout.phone);
        } catch (Exception e) {
            e.printStackTrace();
        }
        loginDlg.dismiss();


    }

    private void tips(int tip, int style) {
        if (loginDlg != null && loginDlg.isShowing()) loginDlg.dismiss();
        loginDlg = new Dialog(this, style);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.tips_dialog, null);
        loginDlg.setContentView(view);
        ((TextView) view.findViewById(R.id.text)).setText(getResources().getString(tip));
        Window alertWindow = loginDlg.getWindow();
        WindowManager windowManager = this.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lParams = alertWindow.getAttributes();
        lParams.height = (int) (display.getHeight() * 0.4);
        lParams.width = (int) (display.getWidth() * 0.8);
        Log.e("lParams", "" + lParams.height + "==" + lParams.width);
        alertWindow.setAttributes(lParams);
        loginDlg.show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (loginDlg != null && loginDlg.isShowing()) loginDlg.dismiss();
    }

    @Override
    protected Object initFragmentFilter() {
        FragmentPresenter p = ((FragmentPresenter) getPresenter(R.id.container));
        String tag = p.getForceTag();
        return tag;
    }

    @Override
    protected boolean FragmentBackFilterCondition(Object o) {
        String tag = (String) o;
        return tag == null || tag.equals("null") || tag.equals("") || !tag.equals("login");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (loginDlg != null && loginDlg.isShowing()) loginDlg.dismiss();
    }

    @Override
    public void receiverNotify(String tag, View view, int layout) {
        int id = -1;
        try {
            if (layout == R.layout.reset_pw) {
                id = R.id.reset_submit;
            } else if (layout == R.layout.phone) {
                id = R.id.next_step;
            } else if (layout == R.layout.input_us_pw) {
                id = R.id.submit;
            }
            addOnclick(view.findViewById(id));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void disconnect() {

    }

    @Override
    public void connect() {

    }

    @Override
    public void button_positive(DialogInterface dialogInterface) {

    }

    @Override
    public void button_negative(DialogInterface dialogInterface) {

    }

    @Override
    public void button_neutral(DialogInterface dialogInterface) {

    }

    @Override
    public void before(EditText editText, String s, int i) throws Exception {

    }

    @Override
    public void onChange(EditText editText, String s, int i) throws Exception {

    }

    @Override
    public void after(EditText editText, String s, int i) throws Exception {

    }


}
