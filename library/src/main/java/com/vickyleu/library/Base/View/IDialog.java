package com.vickyleu.library.Base.View;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;


public class IDialog implements DialogInterface.OnDismissListener, DialogInterface.OnCancelListener {
    View view;
    public IBuilder builder;
    Context mContext;
    int mStyle = -1;
    AlertDialog dialog;
    public IDialog(boolean normal, Context context, int resId, int... style) {
        this(normal,context, ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(resId, null)
               ,style);
    }


    public IDialog(boolean normal, Context context, View view,int... style) {
        this.mContext = context;
        this.view = view;

        builder = null;

//        if (normal){
//            if (style.length != 0){
//                builder=new IBuilder(this.mContext, mStyle);
//            }else {
//                builder=new IBuilder(this.mContext);
//            }
//        }else {
            if (style.length != 0) {
                this.mStyle = style[0];
                builder = addBuilder(this.mContext, mStyle);
            } else {
                builder = addBuilder(this.mContext);
            }
//        }
    }

    private IBuilder addBuilder(Context context) {
        return new IBuilder(context);
    }

    private IBuilder addBuilder(Context context, int style) {
        return new IBuilder(context, style);
    }

    public IBuilder IBuilder() {
        return builder;
    }


    public void setContentView(View view) {
        dialog.setContentView(view);
    }

    public void show() {
        dialog.show();
        dialog.setOnDismissListener(this);
        dialog.setOnCancelListener(this);
    }

    public void Dismiss() {
        dialog.dismiss();
        mStyle = -1;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {

    }

    @Override
    public void onCancel(DialogInterface dialog) {

    }

    public class IBuilder extends AlertDialog.Builder {

        public IBuilder(Context context) {
            super(context);
        }

        public IBuilder(Context context, int theme) {
            super(context, theme);
        }

        public IBuilder setDialogMessage(String msg) {
            builder.setMessage(msg);
            return builder;
        }

        public IBuilder setDialogTitle(String title) {
            builder.setTitle(title);
            return builder;
        }

//        @Override
//        public AlertDialog.Builder setCancelable(boolean cancelable) {
//            return builder.setCancelable(cancelable);
//        }

        public IBuilder DialogInit() {
            dialog = builder.create();
            setContentView(view);
            return builder;
        }

        @Override
        public AlertDialog show() {
            return null;
        }
    }
}
