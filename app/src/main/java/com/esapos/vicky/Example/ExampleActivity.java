package com.esapos.vicky.Example;

import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.esapos.vicky.R;
import com.vickyleu.library.Base.View.BaseActivity;
import com.vickyleu.library.Base.model.HttpLibrary.HttpResponseModel;
import com.vickyleu.library.Base.model.RecyclerViewType;

import java.util.ArrayList;
import java.util.List;

public class ExampleActivity extends BaseActivity {

    @Override
    public void createLayout() {

        layout(R.layout.activity_main, false);
    }

    @Override
    public void initView() {

        List<String> list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            list.add("http://pic32.nipic.com/20130829/12906030_124355855000_2.png");
        }
        ExampleAdapter adapter = new ExampleAdapter(list, this);
        setRecyclerView(toView(RecyclerView.class, R.id.recycler), RecyclerViewType.StaggeredGridLayout, adapter);
    }

    @Override
    protected void onClickEvent(int i) {

    }

    @Override
    public void httpErr(String s, HttpResponseModel httpResponseModel) {

    }

    @Override
    public void httpSuccess(String s, HttpResponseModel httpResponseModel) {

    }

    @Override
    public void receiverNotify(String tag, View view, int layout) {

    }

    @Override
    public void disconnect() {

    }

    @Override
    public void connect() {

    }

    @Override
    protected Object initFragmentFilter() {
        return null;
    }

    @Override
    protected boolean FragmentBackFilterCondition(Object o) {
        return false;
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
