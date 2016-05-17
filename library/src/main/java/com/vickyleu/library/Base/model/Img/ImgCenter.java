package com.vickyleu.library.Base.model.Img;

import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;


public abstract class ImgCenter {
    Context mContext;
    ImageLoaderConfiguration config;

    public ImgCenter(Context context, boolean useNoraml) {
        this.mContext = context;
        if (useNoraml){
            config = getConfig();
            initImageLoader(config,mContext);
        }
        Customer();
    }

    protected void initImageLoader(ImageLoaderConfiguration configuration, Context context) {
        if (configuration == null)configuration = getImageLoaderConfiguration(context);
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(configuration);
    }

    protected void fromAppConfig(ImageLoaderConfiguration configuration){
        ImageLoader.getInstance().init(configuration);
    }


    protected abstract ImageLoaderConfiguration getConfig();

    private ImageLoaderConfiguration getImageLoaderConfiguration(Context context) {
        return new ImageLoaderConfiguration.Builder(
                context).threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheSize(50 * 1024 * 1024)
                // 50 Mb
                .tasksProcessingOrder(QueueProcessingType.LIFO)
//                .writeDebugLogs() // Remove for release app
                .build();
    }

    public abstract ImgCenter Customer();
}
