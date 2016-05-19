package com.esapos.vicky.Model;

import android.os.Handler;

import java.util.concurrent.Delayed;

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
public class IConstant {

    static Handler handler=new Handler();

    public static void handler(Runnable r,int delayed) {
        handler.postDelayed(r,delayed);
    }
}
