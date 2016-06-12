package com.vickyleu.library.Base.model.Socket;

import android.content.Intent;

import com.vickyleu.library.Base.model.AppCenter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.Future;

/**
 * @Created by VickyLeu on 2016/5/19.
 * @Author VickyLeu
 * @Companny Esapos
 */
public class SocketHelper {

    static SocketHelper helper;
    private SoThread thread;
    private SocketClientResponse mResponse;

    public static SocketHelper init() {
        if (helper == null) helper = new SocketHelper();
        return helper;
    }

    private SocketHelper() {
    }

    public void openServer(SocketServer.SocketResponse response, int port) {
        Intent intent = new Intent(AppCenter.getInstant(), SocketServer.class);
        AppCenter.getInstant().startService(intent);
        SocketServer.OpenResponse(response).startServer(port);
    }


    public void closeServer() {
        SocketServer.CloseResponse();
    }

    public void openClient(SocketClientResponse response,final String iNet, final int port){
        mResponse=response;
        if (thread == null) {
            thread = new SoThread(new Runnable() {
                @Override
                public void run() {
                    Future<Socket> task = SocketFuture.Connect(iNet,port);
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
            String str = null;
            buffer = input.readUTF();
            if (mResponse != null) {
                str = mResponse.onClientResponse(buffer);
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
    public interface SocketClientResponse {
        String onClientResponse(String response);
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
        }

        public void setFlag(boolean flag) {
            interrupt();
            this.flag = flag;
        }
    }


}
