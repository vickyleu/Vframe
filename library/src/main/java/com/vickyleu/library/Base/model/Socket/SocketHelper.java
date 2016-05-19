package com.vickyleu.library.Base.model.Socket;

import android.content.Intent;

import com.vickyleu.library.Base.model.AppCenter;

/**
 * @Created by VickyLeu on 2016/5/19.
 * @Author VickyLeu
 * @Companny Esapos
 */
public class SocketHelper {

    static SocketHelper helper;

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


}
