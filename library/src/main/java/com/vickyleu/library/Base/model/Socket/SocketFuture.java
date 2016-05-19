package com.vickyleu.library.Base.model.Socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class SocketFuture {
    private static ExecutorService exe;
    static ServerSocket ss;

    private SocketFuture() {
    }

    public static Future<Socket> Connect(String iNet, int port) {

        if (exe != null && !exe.isShutdown()) exe.shutdown();
        exe = Executors.newSingleThreadExecutor();
        Future<Socket> result = exe.submit(new ClientTask(iNet, port));
        return result;
    }

    public static void ReleaseSocket() {
        if (ss != null) try {
            ss.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected static Future<Socket> Server(int port) {

        if (exe != null && !exe.isShutdown()) exe.shutdown();
        exe = Executors.newSingleThreadExecutor();
        if (ss == null) {
            try {
                ss = new ServerSocket();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Future<Socket> result = exe.submit(new ServerTask(port));
        return result;
    }

    private static class ClientTask implements Callable<Socket> {
        String iNet;
        int port;

        public ClientTask(String iNet, int port) {
            this.iNet = iNet;
            this.port = port;
        }

        @Override
        public Socket call() throws Exception {
            Socket mSocket = new Socket();
            try {
                mSocket.connect(new InetSocketAddress(iNet, port), 3000);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
            return mSocket;
        }
    }

    private static class ServerTask implements Callable<Socket> {
        int port;


        public ServerTask(int port) {
            this.port = port;
        }

        @Override
        public Socket call() throws Exception {
            Socket mSocket = null;
            try {
                if (!ss.isBound()) ss.bind(new InetSocketAddress(port));
                mSocket = ss.accept();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
            return mSocket;
        }
    }
}
