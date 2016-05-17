package com.vickyleu.library.Base.model.HttpLibrary;


import android.os.Message;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

final class BaseHttpRequest extends Thread {
    int timeout;
    String requestUrl;
    Object params;
    BaseHttpHandler callBack;
    int which;
    Map<String, Object> attachParams;
    private String requestTag;
    private boolean requesting;

    public void setRequesting(boolean requesting) {
        this.requesting = requesting;
    }

    BaseHttpRequest(String requestUrl, Object params, BaseHttpHandler callBack, int timeout, int which, Map<String, Object> attachParams) {
        this.requestUrl = requestUrl;
        this.params = params;
        this.callBack = callBack;
        this.which = which;
        this.attachParams = attachParams;
        this.requestTag = requestUrl + which;
        this.timeout=timeout;
    }

    public void run() {
        super.run();
        this.requesting = true;
        Logger.log("requesturl:" + this.requestUrl);
        Logger.log("requestParams:" + this.params);

        try {
            this.post();
        } catch (Exception var2) {
            var2.printStackTrace();
        }

        this.requesting = false;
        BaseHttpConnectPool.removeRequest(this.requestTag);
    }

    private final void post() {
        Message message = new Message();
        HttpResponseModel model = new HttpResponseModel(this.requestUrl, (byte[]) null, this.which, this.attachParams);
        message.obj = model;

        try {
            URL e = new URL(this.requestUrl);
            HttpURLConnection connection = (HttpURLConnection) e.openConnection();
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(timeout);
            connection.setReadTimeout(timeout);
            connection.setDoOutput(true);
            connection.connect();
            OutputStream outputStream = connection.getOutputStream();
            byte[] reqParams = objToByteArray(this.params);
            if (reqParams != null) {
                outputStream.write(reqParams);
                outputStream.flush();
            }

            if (connection != null) {
                Logger.log("responseCode:" + connection.getResponseCode());
                if (connection.getResponseCode() != 200) {
                    if (this.callBack != null) {
                        message.what =HttpResponseMsgType.RESPONSE_ERR;
                        model.setResponse("Service Inner Err".getBytes());
                        this.callBack.sendMessage(message);
                    }
                } else {
                    BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuffer data = new StringBuffer();

                    String line;
                    while ((line = br.readLine()) != null && this.requesting) {
                        data.append(line);
                    }

                    Logger.log(new String(data));
                    if (this.requesting) {
                        if (this.callBack != null) {
                            message.what = HttpResponseMsgType.RESPONSE_SUCCESS;
                            model.setResponse(data.toString().getBytes());
                            this.callBack.sendMessage(message);
                        }
                    } else {
                        Logger.log("request cancle");
                    }
                }

                connection.disconnect();
            }
        } catch (Exception var10) {
            if (this.callBack != null) {
                message.what = HttpResponseMsgType.RESPONSE_ERR;
                model.setResponse("Connect Time Out".getBytes());
                this.callBack.sendMessage(message);
            }
        }

    }

    private static final byte[] objToByteArray(Object params) {
        try {
            if (params == null) {
                return null;
            }

            if (params instanceof JSONObject) {
                return params.toString().getBytes();
            }

            if (params instanceof JSONArray) {
                return params.toString().getBytes();
            }

            if (params instanceof String) {
                return params.toString().getBytes();
            }

            if (params instanceof Map) {
                JSONObject e = new JSONObject();
                Set set = ((Map) params).entrySet();
                Iterator var4 = set.iterator();

                while (var4.hasNext()) {
                    Map.Entry entry = (Map.Entry) var4.next();
                    Object key = entry.getKey();
                    Object value = entry.getValue();
                    if (key != null) {
                        if (value == null) {
                            e.put(key.toString(), "");
                        } else {
                            e.put(key.toString(), value);
                        }
                    }
                }

                return e.toString().getBytes();
            }

            if (params instanceof byte[]) {
                return (byte[]) params;
            }
        } catch (Exception var7) {
            var7.printStackTrace();
        }

        return null;
    }
}

