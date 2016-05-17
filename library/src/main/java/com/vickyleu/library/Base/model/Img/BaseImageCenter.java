package com.vickyleu.library.Base.model.Img;

import android.content.Context;

import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;


public abstract class BaseImageCenter extends ImgCenter {
    public BaseImageCenter(Context context, boolean useNoraml) {
        super(context, useNoraml);
    }

    @Override
    protected ImageLoaderConfiguration getConfig() {
        return getImageConfig();
    }

    protected abstract ImageLoaderConfiguration getImageConfig();


    public void setConfig(ImageLoaderConfiguration configuration){
        fromAppConfig(configuration);
    }
    @Override
    public BaseImageCenter Customer() {
        CustomerOtherImgFramework();
        return null;
    }

    protected abstract void CustomerOtherImgFramework();
}
