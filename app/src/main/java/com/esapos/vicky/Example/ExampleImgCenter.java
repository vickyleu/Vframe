package com.esapos.vicky.Example;

import android.content.Context;

import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.vickyleu.library.Base.model.Img.BaseImageCenter;


public class ExampleImgCenter extends BaseImageCenter {
    public ExampleImgCenter(Context context, boolean useNoraml) {
        super(context, useNoraml);
    }

    @Override
    protected ImageLoaderConfiguration getImageConfig() {
        return null;
    }

    @Override
    protected void CustomerOtherImgFramework() {

    }
}
