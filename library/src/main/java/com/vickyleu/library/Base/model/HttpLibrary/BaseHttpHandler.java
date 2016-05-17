package com.vickyleu.library.Base.model.HttpLibrary;


import android.os.Handler;
import android.os.Message;

public final class BaseHttpHandler extends Handler {
    HttpHandler httpHandler;

    public BaseHttpHandler(HttpHandler httpHandler) {
        this.httpHandler = httpHandler;
    }

    public final void handleMessage(Message msg) {
        super.handleMessage(msg);
        if (msg != null) {
            if (this.httpHandler != null) {
                int what = msg.what;
                switch (what) {
                    case HttpResponseMsgType.RESPONSE_ERR:
                        try {
                            this.httpHandler.httpErr((HttpResponseModel) msg.obj);
                        } catch (Exception var5) {
                            var5.printStackTrace();
                        }
                        break;
                    case HttpResponseMsgType.RESPONSE_SUCCESS:
                        try {
                            this.httpHandler.httpSuccess((HttpResponseModel) msg.obj);
                        } catch (Exception var4) {
                            var4.printStackTrace();
                        }
                }

            }
        }
    }
}

