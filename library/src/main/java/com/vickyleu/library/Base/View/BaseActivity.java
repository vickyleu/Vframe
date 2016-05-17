package com.vickyleu.library.Base.View;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.vickyleu.library.Base.Controller.BaseRecyclerAdapter;
import com.vickyleu.library.Base.Presenter.CycleCallBack;
import com.vickyleu.library.Base.Presenter.IPresenter;
import com.vickyleu.library.Base.View.CallBack.Dlgbutton;
import com.vickyleu.library.Base.View.CallBack.Wathcer;
import com.vickyleu.library.Base.model.HttpLibrary.BaseHttpConnectPool;
import com.vickyleu.library.Base.model.HttpLibrary.BaseHttpHandler;
import com.vickyleu.library.Base.model.HttpLibrary.HttpHandler;
import com.vickyleu.library.Base.model.HttpLibrary.HttpResponseModel;
import com.vickyleu.library.Base.model.Merge.IView;
import com.vickyleu.library.Base.model.RecyclerViewType;
import com.vickyleu.library.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public abstract class BaseActivity extends AppCompatActivity implements
        View.OnClickListener, HttpHandler, TextWatcher, View.OnFocusChangeListener, Dlgbutton, Wathcer {
    protected AlertDialog adlg;
    protected int _res;
    protected Intent intent;
    protected DialogType type;
    boolean _backClose;
    protected Dlgbutton dlgbutton;
    BaseHttpConnectPool pool;
    private int currIndex = -1;
    private Wathcer wathcer;
    private Map<Integer, IPresenter> presenterMap = new HashMap<>();
    CycleCallBack cycle;
    private IView iView;


    public abstract void createLayout();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createLayout();
    }

    public final void layout(int layoutResID, boolean backClose) {
        _res = layoutResID;
        _backClose = backClose;
        setRes(_res);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        initView();
    }

    public abstract void initView();

    protected final <T extends RecyclerView, D, VH extends RecyclerView.ViewHolder>
    void setRecyclerView(T t, RecyclerViewType type, BaseRecyclerAdapter<D, VH> adapter) {
        if (type == RecyclerViewType.LinearLayout) {
            t.setLayoutManager(new LinearLayoutManager(this));
        } else if (type == RecyclerViewType.GridLayout) {
            t.setLayoutManager(new GridLayoutManager(this, 3));
        } else {
            t.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        }
        t.setAdapter(adapter);

    }

    public final void registerIP_C(IView iView) {
        this.iView = iView;
    }

    protected final <V extends View, D> void setPresenter(IPresenter<V, D> presenter, int resId) {
        presenterMap.put(resId, presenter);
        cycle = presenter.getCallBack();
    }

    protected final IPresenter getPresenter(int key) {
        return presenterMap.get(key);
    }

    protected void addItemClick(BaseRecyclerAdapter adapter) {

        adapter.setOnItemClickListener();
    }

    protected void addLongClick(BaseRecyclerAdapter adapter) {
        adapter.setOnItemLongClickListener();
    }


    final synchronized Object loadForceData(String... key) {
        if (key == null || key.length == 0) return null;
        Object ob = null;
        if (intent == null) intent = getIntent();
        if (key.length > 1) {
            Bundle bundle = intent.getBundleExtra(key[0]);
            ob = bundle.getSerializable(key[1]);
        } else {
            ob = intent.getStringExtra(key[0]);
        }
        return ob;
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        startTransitionAnim();
    }

    protected final FragmentManager getV4Manager() {
        return getSupportFragmentManager();
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        startTransitionAnim();
    }

    public void startTransitionAnim() {

    }

    @Override
    public void onClick(View v) {
        onClickEvent(v.getId());
    }


    protected final void setRes(int layoutResID) {
        setContentView(layoutResID);
    }

    protected abstract void onClickEvent(int viewId);



    protected final void addReq(String url, Object param, int timeout) {
        if (pool == null) pool = BaseHttpConnectPool.getInstance();
        pool.addRequest(url, param, new BaseHttpHandler(this), timeout);
        showDialog(1, getResources().getString(R.string.please_wait));
    }

    protected final void addReq(String url, Object param) {
        this.addReq(url, param, 5000);
    }

    @Override
    public void httpErr(HttpResponseModel model) throws Exception {
        httpErr(model.getRequestUrl(), model);
    }

    @Override
    public void httpSuccess(HttpResponseModel model) throws Exception {
        httpSuccess(model.getRequestUrl(), model);
    }


    protected final <T> T toView(Class<T> clazz, int id) {
        T d = null;
        try {
            d = (T) findViewById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return d;
    }

    protected void refreshView(View view, int resId, int layout) {
        refreshIView(view, resId, layout);
    }

    private void refreshIView(View view, int resId, int layout) {
        if (iView != null)
            iView.notifyIP_C(view, resId, layout);
    }


    public abstract void httpErr(String url, HttpResponseModel model);

    public abstract void httpSuccess(String url, HttpResponseModel model);


    public final <T> T parserForBean(String jsonStr, Class<T> clazz) {
        T t = null;
        try {
            Gson gson = new Gson();
            t = gson.fromJson(jsonStr, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    public final JSONObject parserForJson(String jsonStr) {
        JSONObject json = null;
        try {
            json = new JSONObject(jsonStr);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }



    protected void showDialog(int type, String... content) {
//        int theme = -1;
//        if (type == 1) {
//            this.type = DialogType.NETWORK;
//            theme=android.R.style.Theme_Black;
//        }

//        DialogInterface.OnClickListener click= new DialogInterface.OnClickListener() {
//            @Override
//            public void onItemClick(DialogInterface dialog, int which) {
//                if (which == DialogInterface.BUTTON_POSITIVE) dlgbutton.button_positive(dialog);
//                if (which == DialogInterface.BUTTON_NEGATIVE) dlgbutton.button_negative(dialog);
//                if (which == DialogInterface.BUTTON_NEUTRAL) dlgbutton.button_neutral(dialog);
//            }
//        };
//        AlertDialog.Builder builder = new AlertDialog.Builder(this, theme);
//        builder.setPositiveButton(null, click);
//
//
//
//        if (adlg == null) {
//            adlg = builder.create();
//        } else {
//            if (adlg.isShowing()) adlg.dismiss();
//            adlg = builder.create();
//        }
//        builder.setMessage(content[0]);
//        if (content.length > 1) builder.setTitle(content[1]);
//        builder.show();
    }


    private final void isBreak() {
        if (pool != null)
            pool.removeAllRequest();
        if (adlg != null)
            adlg.dismiss();
        disconnect();
    }

    private final void isConn() {
        connect();
    }

    /**
     */
    protected void dismissDialog() {
//        if (adlg != null && adlg.isShowing()) adlg.dismiss();
//        type = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (pool != null)
            pool.removeAllRequest();
        dismissDialog();
        if (presenterMap != null && !presenterMap.isEmpty()) {
            for (Map.Entry<Integer, IPresenter> set : presenterMap.entrySet()) {
                if (set.getValue() != null) {
                    set.getValue().onDestroy();
                }
            }
        }
        if (cycle != null)
            cycle.onDestroy();
    }


    /**
     */
    protected final void addOnclick(View... v) throws Exception {
        if (v.length < 1) throw new Exception(getString(R.string.too_less));
        for (int i = 0; i < v.length && v != null && v.length != 0; i++) {
            v[i].setOnClickListener(this);
        }
    }

    protected final void addOnclick(int... resId) throws Exception {
        if (resId.length < 1) throw new Exception(getString(R.string.too_less));
        for (int i = 0; i < resId.length && resId != null && resId.length != 0; i++) {
            findViewById(resId[i]).setOnClickListener(this);
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        try {
            wathcer.before((EditText) findViewById(currIndex), ((EditText) findViewById(currIndex)).getText().toString(), currIndex);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        try {
            wathcer.onChange((EditText) findViewById(currIndex), ((EditText) findViewById(currIndex)).getText().toString(), currIndex);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        try {
            wathcer.after((EditText) findViewById(currIndex), ((EditText) findViewById(currIndex)).getText().toString(), currIndex);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            currIndex = v.getId();
        }
    }

    public abstract void receiverNotify(String mTag, View view, int layout);


    protected enum DialogType {
        NETWORK, TOAST, TIPS
    }

    protected final DialogType getCurDlg() {
        return type;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (_backClose) {
                return super.onKeyDown(keyCode, event);
            } else {
                if (getV4Manager().getBackStackEntryCount() > 0) {
                    return super.onKeyDown(keyCode, event);
                }
                return exitMethod();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    protected boolean exitMethod() {
        return false;
    }

    protected final void addWatcher(Wathcer wathcer, int... resId) throws Exception {
        if (resId.length < 1) throw new Exception(getString(R.string.too_less));
        this.wathcer = wathcer;
        for (int i = 0; i < resId.length; i++) {
            EditText edt = ((EditText) findViewById(resId[i]));
            edt.addTextChangedListener(this);
            edt.setOnFocusChangeListener(this);
        }
    }


    public abstract void disconnect();

    public abstract void connect();

    @Override
    protected void onPause() {
        super.onPause();
        if (cycle != null)
            cycle.onPause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (cycle != null)
            cycle.onRestart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (cycle != null)
            cycle.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (cycle != null)
            cycle.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (cycle != null)
            cycle.onResume();
    }


    @Override
    public void onBackPressed() {
        if (pool != null) pool.removeAllRequest();
        onFragmentBack(initFragmentFilter(), getV4Manager().getBackStackEntryCount(), _backClose);
    }

    protected abstract Object initFragmentFilter();

    protected abstract boolean FragmentBackFilterCondition(Object filter);

    protected final void onFragmentBack(Object filter, int backStackEntryCount, boolean _backClose) {
        boolean condition = FragmentBackFilterCondition(filter);
        if (condition) {
            if (backStackEntryCount == 1) {
                if (_backClose) finish();
            } else {
                if (!getV4Manager().popBackStackImmediate()) {
                    supportFinishAfterTransition();
                }
            }
        } else {
            if (_backClose) finish();
        }
    }
}
