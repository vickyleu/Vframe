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

public class SocketServer extends Service {

    static SocketResponse mResponse;
    SoThread thread;

    private SocketServer() {
    }

    static SocketServer socketServer;

    @Override
    public void onCreate() {
        super.onCreate();
        socketServer = this;
        if (thread != null)
            thread.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (thread != null) thread.setFlag(false);

    }

    public final void startServer(final int port) {
        if (thread == null) {
            thread = new SoThread(new Runnable() {
                @Override
                public void run() {
                    Future<Socket> task = SocketFuture.Server(port);
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

    public final void stopServer() {
        stopSelf();
    }

    static final SocketServer OpenResponse(SocketResponse response) {
        mResponse = response;
        return socketServer;
    }

    static final void CloseResponse() {
        mResponse = null;
    }

    private void readStream(DataInputStream input, DataOutputStream output, Socket socket) {
        String buffer = null;
        try {
            String str = null;
            buffer = input.readUTF();
            if (mResponse != null) {
                str = mResponse.onResponse(buffer);
            }
            if (str != null && !str.equals("")) {
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
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public interface SocketResponse {
        String onResponse(String response);
    }


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
            SocketFuture.ReleaseSocket();
        }

        public void setFlag(boolean flag) {
            interrupt();
            this.flag = flag;
        }
    }
}
