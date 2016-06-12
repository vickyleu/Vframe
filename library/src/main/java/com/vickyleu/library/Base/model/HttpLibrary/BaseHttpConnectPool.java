package com.vickyleu.library.Base.model.HttpLibrary;


import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public final class BaseHttpConnectPool {
    private static Map<String, BaseHttpRequest> httpRequests = new ConcurrentHashMap();

    public static BaseHttpConnectPool getInstance() {
        return Hcp.httpConnectionPool;
    }

    private BaseHttpConnectPool(BaseHttpConnectPool baseHttpConnectPool) {
    }

    public final void removeAllRequest() {
        try {
            Set e = httpRequests.entrySet();

            for (Object obj : e) {
                Map.Entry entry = (Map.Entry) obj;
                ((BaseHttpRequest) entry.getValue()).setRequesting(false);
            }

            httpRequests.clear();
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }

    private void removeDieRequest() {
        try {
            Set e = httpRequests.entrySet();

            for (Object obj : e) {
                Map.Entry entry = (Map.Entry) obj;
                if (!((BaseHttpRequest) entry.getValue()).isAlive()) {
                    httpRequests.remove(entry.getKey());
                }
            }
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }

    public static void removeRequest(String requestTag) {
        if (requestTag != null) {
            try {
                if (httpRequests.containsKey(requestTag)) {
                    httpRequests.remove(requestTag);
                }
            } catch (Exception var2) {
                var2.printStackTrace();
            }

        }
    }

    public synchronized void addRequest(String requestUrl, Object params, BaseHttpHandler callBack, int timeout, int which, Map<String, Object> attachParams) {
        synchronized (this) {
            Logger.log("requestTime:" + System.currentTimeMillis());
            this.removeDieRequest();
            String requestTag = requestUrl;
            if (which != -1) {
                requestTag = requestUrl + which;
            }

            if (httpRequests.containsKey(requestTag)) {
                Logger.log("requestNotAllow");
            } else {
                BaseHttpRequest baseHttpRequest = new BaseHttpRequest(requestUrl, params, callBack, timeout, which, attachParams);
                httpRequests.put(requestTag, baseHttpRequest);
                baseHttpRequest.start();
            }
        }
    }

    public synchronized void addRequest(String requestUrl, Object params, BaseHttpHandler callBack, int timeout, int which) {
        this.addRequest(requestUrl, params, callBack, timeout, which, (Map) null);
    }

    public synchronized void addRequest(String requestUrl, Object params, BaseHttpHandler callBack, int timeout) {
        this.addRequest(requestUrl, params, callBack, timeout, -1, (Map) null);
    }

    public synchronized void addRequest(String requestUrl, Object params, BaseHttpHandler callBack, Map<String, Object> attachParams) {
        this.addRequest(requestUrl, params, callBack, 10000, -1, attachParams);
    }

    private static class Hcp {
        static BaseHttpConnectPool httpConnectionPool = new BaseHttpConnectPool((BaseHttpConnectPool) null);

        private Hcp() {
        }
    }
}

