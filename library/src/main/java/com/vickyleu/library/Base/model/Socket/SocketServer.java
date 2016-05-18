package com.vickyleu.library.Base.model.Socket;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.Future;

/**
 * @Created by VickyLeu on 2016/5/18.
 * @Author VickyLeu
 * @Companny Esapos
 */
public abstract class SocketServer extends Service {


    SoThread thread;

    @Override
    public void onCreate() {
        super.onCreate();
        if (thread != null)
            thread.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (thread != null)
            thread.setFlag(false);
    }

    public final void startServer(final int port) {
        if (thread == null){
            thread = new SoThread(new Runnable() {
                @Override
                public void run() {
                    Future<Socket> task = SocketHelper.Server(port);
                    if (task == null) return;
                    Socket so = null;
                    try {
                        so = task.get();
                        if (so == null) return;
                        DataOutputStream output = new DataOutputStream(so.getOutputStream());
                        DataInputStream input = new DataInputStream(so.getInputStream());
                        readStream(input, output, so);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }
            });
        }
    }

    private void readStream(DataInputStream input, DataOutputStream output, Socket socket) {
        String buffer = null;
        try {
            buffer = input.readUTF();
            String str = response(buffer);
            if (str!=null){
                output.writeUTF(str);
                output.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            input.close();
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    protected abstract String response(String response);

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    class SoThread extends Thread {
        public SoThread(Runnable runnable) {
            super(runnable);
        }

        boolean flag = true;

        @Override
        public void run() {
            while (flag) {
                super.run();
            }
            SocketHelper.ReleaseSocket();
        }

        public void setFlag(boolean flag) {
            interrupt();
            this.flag = flag;
        }
    }
}
