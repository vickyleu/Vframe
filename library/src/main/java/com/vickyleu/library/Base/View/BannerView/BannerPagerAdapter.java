package com.vickyleu.library.Base.View.BannerView;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BannerPagerAdapter extends PagerAdapter {
    List<String> list;
    Context mContext;
    Dot dot;
    BaseBannerView bannerView;
    Map<Integer,ViewHolder> map=new HashMap<>();

    public BannerPagerAdapter(List<String> list, Context mContext, Dot dot, BaseBannerView bannerView) {
        this.list = list;
        this.mContext = mContext;
        this.dot = dot;
        this.bannerView = bannerView;
    }

    public void add(String t) {
        if (list == null) list = new ArrayList<>();
        list.add(t);
        notifyDataSetChanged();
    }

    public void addALL(List<String> list) {
        if (list != null)
            this.list = list;
        notifyDataSetChanged();
    }

    public void delete(String t) {
        if (list != null && !list.isEmpty()) {
            list.remove(t);
        }
        notifyDataSetChanged();
    }

    public void clear() {
        this.list = null;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        if (list == null || list.isEmpty()) return 0;
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ViewHolder holder=null;
        if (map.get(position)==null){
            ImageView view = new ImageView(mContext);
            ViewGroup.LayoutParams pm = bannerView.getLayoutParams();
            view.setLayoutParams(pm);
            holder=new ViewHolder();
            holder.imageView=view;
            map.put(position,holder);
        }else {
            holder=map.get(position);
        }
        ImageLoader loader = ImageLoader.getInstance();
        loader.displayImage(list.get(position),holder.imageView);
        dot.show(position);
        return holder.imageView;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object view) {
        ((ViewPager) container).removeView((View) view);
        dot.hide(position);
    }

    public interface Dot {
        void show(int position);

        void hide(int position);
    }
    class ViewHolder{
        ImageView imageView;
    }
}
