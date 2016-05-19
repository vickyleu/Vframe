package com.esapos.vicky.Example;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.esapos.vicky.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.vickyleu.library.Base.Controller.BaseRecyclerAdapter;
import com.vickyleu.library.Base.Controller.BaseViewType;

import java.util.List;
import java.util.Random;

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
 * <p>
 * =================================================*
 */
public class ExampleAdapter extends BaseRecyclerAdapter<String, ExampleAdapter.Holder> {
    private static final String TAG = ExampleAdapter.class.getSimpleName();
    private Random random;
    private int x;
    private int y;

    public ExampleAdapter(List<String> list, Context mContext) {
        super(list, mContext);
    }

    @Override
    protected void initAdapter(int i, BaseViewType... baseViewTypes) {
        Log.e(TAG, "initAdapter: " );

        random=new Random();
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Display dp = wm.getDefaultDisplay();
        dp.getMetrics(dm);
        x = dm.widthPixels;;
        y = dm.heightPixels;
    }

    @Override
    protected Holder singleType(LayoutInflater layoutInflater, ViewGroup viewGroup) {
        Log.e(TAG, "singleType: " );
        return new Holder(layoutInflater.inflate(R.layout.adapter, viewGroup, false));
    }

    @Override
    protected Holder moreType(LayoutInflater layoutInflater, int i, ViewGroup viewGroup) {
        return null;
    }

    @Override
    protected void bindView(Holder holder, int i, int i1) {

    }

    @Override
    protected void bindView(Holder holder, int i, int i1, String s) {

        Log.e(TAG, "bindView: " );
        ImageLoader loader = ImageLoader.getInstance();
        ImageSize size=new ImageSize( random.nextInt(x/2), random.nextInt(y/4));
        ImageLoadingListener listner=new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {

            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {

            }

            @Override
            public void onLoadingCancelled(String s, View view) {

            }
        };

        loader.loadImage(s,size,listner);
        loader.displayImage(s.toString(),holder.textView);
        setViewType(0);
    }

    @Override
    protected void ListItemClick(View view, String s, int i) {

    }

    @Override
    protected void ListItemLongClick(View view, String s, int i) {

    }

    @Override
    public void receiverNotify(int i, Holder holder, String s, int i1, int i2) {

    }

    @Override
    public void OnViewClickListener(Holder holder, View view, int i) {

    }

    static class Holder extends RecyclerView.ViewHolder {

        ImageView textView;

        public Holder(View itemView) {
            super(itemView);
            textView = (ImageView) itemView.findViewById(R.id.textView);
        }
    }
}
