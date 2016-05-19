package com.esapos.vicky.Example;

import android.content.Context;

import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.vickyleu.library.Base.model.Img.BaseImageCenter;

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
