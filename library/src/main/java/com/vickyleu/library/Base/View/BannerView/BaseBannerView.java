package com.vickyleu.library.Base.View.BannerView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


public abstract class BaseBannerView extends FrameLayout implements BannerPagerAdapter.Dot, ViewPager.OnPageChangeListener, View.OnTouchListener {

    private BannerPagerAdapter adapter;
    private List<String> list;
    private List<Class<?>> objList;
    private ViewPager viewPager;
    private LinearLayout linearLayout;
    private ImageView[] dots;
    long time = 2000;
    Handler mHandler = new Handler();

    Runnable run = new Runnable() {
        @Override
        public void run() {
            if (viewPager != null) {
                int item = viewPager.getCurrentItem() + 1;
                if (viewPager.getChildCount() > item) {
                    viewPager.setCurrentItem(item);
                } else {
                    viewPager.setCurrentItem(0);
                }
                mHandler.removeCallbacksAndMessages(null);
                mHandler.postDelayed(this, time);
            }
        }
    };
    private StringCallback stringCallback;
    private ObjCallback objCallback;

    public BaseBannerView(Context context) {
        super(context);
        viewPager = new ViewPager(context);
        ViewGroup.LayoutParams pm = this.getLayoutParams();
        viewPager.setLayoutParams(pm);

    }

    public final void setData(List<Class<?>> clazz, String invokeMethod) {
        objList = clazz;
        List<String> sl = new ArrayList<>();
        for (int i = 0; i < clazz.size(); i++) {
            Class<?> cl = clazz.get(i);
            Method method = null;
            try {
                method = cl.getClass().getDeclaredMethod(invokeMethod);
                method.setAccessible(true);
                sl.add((String) method.invoke(cl));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        setData(sl);
    }

    public final void setData(List<String> list) {
        this.list = list;
        dots = new ImageView[list.size()];

        if (linearLayout == null) {
            LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            linearLayout = new LinearLayout(getContext());
            lp1.setMargins(0, 3, 0, 3);
            linearLayout.setLayoutParams(lp1);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        } else {
            linearLayout.removeAllViews();
            this.removeView(linearLayout);
        }
        for (int i = 0; i < dots.length; i++) {
            ImageView dot = dots[i];
            dot = new ImageView(getContext());
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            lp.height = 10;
            lp.width = 10;
            dot.setLayoutParams(lp);
            linearLayout.addView(dot);
        }
        initView();
        this.addView(linearLayout);
        View v = addCustomerView();
        this.addView(v != null ? v : null);
        ;
    }

    protected abstract View addCustomerView();

    private void initView() {
        if (adapter == null) {
            adapter = new BannerPagerAdapter(list, getContext(), this, this);
            viewPager.setAdapter(adapter);
            viewPager.addOnPageChangeListener(this);
        } else {
            adapter.notifyDataSetChanged();
            viewPager.invalidate();

        }
        viewPager.setCurrentItem(0);
//        CurrentPosition(viewPager.getCurrentItem());
        viewPager.setOnTouchListener(this);
        mHandler.removeCallbacksAndMessages(null);
        mHandler.postDelayed(run, time);
    }

    @Override
    public void show(int position) {
        if (dots != null && dots.length != 0 && dots.length - 1 >= position) {
            dots[position].draw(c());
            dots[position].draw(dotShow());

        }
    }

    private Canvas c() {
        Paint paint = new Paint();
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        Canvas canvas = new Canvas();
        canvas.drawPaint(paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
        return canvas;
    }

    private Canvas dotShow() {
        Canvas canvas = new Canvas();
        Paint p = new Paint();
        p.setColor(Color.WHITE);
        canvas.drawCircle(0, 0, 5, p);// 小圆
        return canvas;
    }

    @Override
    public void hide(int position) {
        if (dots != null && dots.length != 0 && dots.length - 1 >= position) {
            dots[position].draw(c());
            dots[position].draw(dotHide());

        }
    }

    private Canvas dotHide() {
        Canvas canvas = new Canvas();
        Paint p = new Paint();
        p.setColor(Color.GRAY);
        canvas.drawCircle(0, 0, 5, p);// 小圆
        return canvas;
    }


    private void pagerShow(int position) {
        if (objList != null && !objList.isEmpty() && objList.size() > position) {
            Class<?> s = objList.get(position);
            if (objCallback != null) objCallback.showObj(s, position);
        }
        if (list != null && !list.isEmpty() && list.size() > position) {
            String s = list.get(position);
            if (stringCallback != null) stringCallback.showObj(s, position);
        }
    }

    private void pagerHide(int position) {
        if (objList != null && !objList.isEmpty() && objList.size() > position) {
            Class<?> s = objList.get(position);
            if (objCallback != null) objCallback.hideObj(s, position);
        }
        if (list != null && !list.isEmpty() && list.size() > position) {
            String s = list.get(position);
            if (stringCallback != null) stringCallback.hideObj(s, position);
        }
    }

    public void registerStringCallBack(StringCallback stringCallback) {
        this.stringCallback = stringCallback;
    }

    public void registerObjCallback(ObjCallback objCallback) {
        this.objCallback = objCallback;
    }

    public interface ObjCallback {
        void showObj(Object o, int position);

        void hideObj(Object o, int position);
    }

    public interface StringCallback {
        void showObj(String o, int position);

        void hideObj(String o, int position);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        CurrentPosition(position);
        show(position);
        pagerShow(position);
        if (position == 0) {
            if ((objList) != null && !objList.isEmpty()) {
                pagerHide(objList.size() - 1);
                return;
            }
            if ((list) != null && !list.isEmpty()) pagerHide(list.size() - 1);
        }
    }

    protected abstract void CurrentPosition(int position);

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            mHandler.removeCallbacksAndMessages(null);
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler.postDelayed(run, time);
        }
        return super.onTouchEvent(event);
    }
}
