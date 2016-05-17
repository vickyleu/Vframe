/*
 * Copyright 2015 Eduard Ereza Martínez
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.vickyleu.library.Base.model.Crash;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.vickyleu.library.Base.View.BaseActivity;
import com.vickyleu.library.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public abstract class CrashActivity extends BaseActivity {

    private Map<String, String> info = new HashMap<String, String>();
    private SimpleDateFormat format = new SimpleDateFormat(
            "yyyy-MM-dd-HH-mm-ss");

    private String saveCrashInfo2File(String ex) {
        long timetamp = System.currentTimeMillis();
        String time = format.format(new Date());
        String fileName = "crash-" + time + "-" + timetamp + ".txt";
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            try {
                File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "crash");
                Log.e("CrashHandler", dir.toString());
                if (!dir.exists())
                    dir.mkdir();
                FileOutputStream fos = new FileOutputStream(new File(dir, fileName));
                fos.write(ex.getBytes());
                fos.close();
                return fileName;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     *
     * @param context
     */
    public void collectDeviceInfo(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null"
                        : pi.versionName;
                String versionCode = pi.versionCode + "";
                info.put("versionName", versionName);
                info.put("versionCode", versionCode);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                info.put(field.getName(), field.get("").toString());
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void createLayout() {
        int layoutId = setErrorLayout();
        layout(layoutId, false);
    }

    protected abstract int setErrorLayout();

    @Override
    public void initView() {
        Toast.makeText(this, R.string.crash, Toast.LENGTH_SHORT).show();
        String ex = CrashHandler.getAllErrorDetailsFromIntent(this, getIntent());
        collectDeviceInfo(this);
        saveCrashInfo2File(ex);
        boolean close = setErrorCloseStatus();
        if (close) CrashHandler.closeApplication(this);
    }

    protected abstract boolean setErrorCloseStatus();

    @Override
    public void disconnect() {

    }

    @Override
    public void connect() {

    }

    @Override
    public void button_positive(DialogInterface dialog) {

    }

    @Override
    public void button_negative(DialogInterface dialog) {

    }

    @Override
    public void button_neutral(DialogInterface dialog) {

    }

    @Override
    public void before(EditText editText, String str, int res) throws Exception {

    }

    @Override
    public void onChange(EditText editText, String str, int res) throws Exception {

    }

    @Override
    public void after(EditText editText, String str, int res) throws Exception {

    }
}
