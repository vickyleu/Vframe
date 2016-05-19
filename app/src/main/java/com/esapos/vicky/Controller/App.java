package com.esapos.vicky.Controller;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Handler;

import com.esapos.vicky.R;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.vickyleu.library.Base.model.AppCenter;
import com.vickyleu.library.Base.model.Crash.CrashHandler;
import com.vickyleu.library.Base.model.Img.BaseImageCenter;
import com.vickyleu.library.Base.model.Socket.SocketHelper;
import com.vickyleu.library.Base.model.Socket.SocketServer;

import java.io.File;

/**
 * @Created by VickyLeu on 2016/5/7.
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
 * <p/>
 * =================================================*
 */
public class App extends AppCenter implements SocketServer.SocketResponse {
    @Override
    protected void initApp() {
        //装载崩溃处理器
        CrashHandler.install(this);
        //开启socket推送接收服务
        SocketHelper.init().openServer(this, 8080);
    }

    @Override
    protected void initImgFromApp(BaseImageCenter baseImageCenter) {

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.ic_launcher) // 设置正在加载的图片资源
                .showImageForEmptyUri(android.R.drawable.ic_menu_report_image) // 设置图上资源为空时加载的图片资源
                .showImageOnFail(android.R.drawable.stat_notify_error) // 设置加载失败的图片资源
                .resetViewBeforeLoading(false)  // 设置图片在下载前是否重置
                .delayBeforeLoading(500)//设置延时多少时间后开始下载
                .cacheInMemory(true) // 设置下载的资源是否缓存在内存中
                .cacheOnDisk(true) // 设置下载的资源是否缓存在SD卡中
//                .preProcessor(...)  // 设置图片加入缓存前，对bitmap进行设置
//        .postProcessor(...) // 设置显示前的图片，显示后这个图片一直保留在缓存中
//        .extraForDownloader(...) // 设置额外的内容给ImageDownloader
//        .considerExifParams(false) // 是否考虑JPEG图像EXIF参数（旋转，翻转）
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2) // 设置图片以何种编码方式显示
                .bitmapConfig(Bitmap.Config.ARGB_8888) // 设置图片的解码类型
//                .decodingOptions(...) // 设置图片的解码配置
//        例如 android.graphics.BitmapFactory.Options decodingOptions
                .displayer(new SimpleBitmapDisplayer()) // 设置显示方式（样式或者动画等）
//        例如：RoundedBitmapDisplayer（int roundPixels）设置圆角图片
//        RoundedBitmapDisplayer(int radian) 设置为圆角，弧度为多少
//        FadeInBitmapDisplayer（int durationMillis）设置图片渐显的时间
//        SimpleBitmapDisplayer()正常显示一张图片
                .handler(new Handler()) // Handler事件
                .build();

        File cacheDir = StorageUtils.getCacheDirectory(this);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
//                .memoryCacheExtraOptions(maxWidth, maxHeight) // 缓存图片的最大宽度，最大高度,
                .diskCacheExtraOptions(480, 800, null)
                .defaultDisplayImageOptions(options)
//                .taskExecutor(...)  //  添加线程池，进行下载,如果进行了这个设置，
//        那么threadPoolSize(int)，threadPriority(int)，
//        tasksProcessingOrder(QueueProcessingType)将不会起作用
//                .taskExecutorForCachedImages(...)// 下载缓存图片
                .threadPoolSize(3) // 设置用于显示图片的线程池大小
                .threadPriority(Thread.NORM_PRIORITY - 1) //  设置线程的优先级
                .tasksProcessingOrder(QueueProcessingType.FIFO) // 设置图片加载和显示队列处理的类型
                // 默认为QueueProcessingType.FIFO
                // 注:如果设置了taskExecutor或者
//        taskExecutorForCachedImages 此设置无效
                .denyCacheImageMultipleSizesInMemory() // 设置是否允许在内存中缓存多个图片大小
//        默认为允许, (同一个图片URL) 根据不同大小
//        的imageview保存不同大小图片
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))// 设置内存缓存
//        默认为一个当前应用可用内存的1 / 8 大小的
                .memoryCacheSize(2 * 1024 * 1024)// 设置SD缓存的最大大小 默认为一个当前应用可用内存的1/8
                .memoryCacheSizePercentage(13) // 设置内存缓存最大大小占当前应用可用内存的百分比
//        默认为一个当前应用可用内存的1 / 8
                .diskCache(new UnlimitedDiskCache(cacheDir)) // default
                .diskCacheSize(50 * 1024 * 1024)
                .diskCacheFileCount(100)
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
                .imageDownloader(new BaseImageDownloader(this)) // 设置图片下载器
                .imageDecoder(new BaseImageDecoder(false)) // 设置图片解码器
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // 设置默认的图片显示选项
                .writeDebugLogs() // 打印DebugLogs
                .build();

        baseImageCenter.setConfig(config);

    }

    @Override
    protected String setName() {
        return null;
    }

    @Override
    protected int setVersion() {
        return 0;
    }

    @Override
    protected BaseImageCenter setImgCenter() {
        return null;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @Override
    public String onResponse(String response) {
        return null;
    }
}
