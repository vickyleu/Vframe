//package com.vickyleu.library.Base.model.Socket;
//
//import android.os.AsyncTask;
//
//import java.io.IOException;
//import java.net.InetSocketAddress;
//import java.net.ServerSocket;
//import java.net.Socket;
//import java.net.SocketAddress;
//import java.util.concurrent.Callable;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.Future;
//
///**
// * Created by Administrator on 2016/5/17.
// */
//public class SocketHelper {
//    private static ExecutorService exe;
//
//    public static Future<Socket> Connect(String iNet, int port) {
//
//        if (exe != null && !exe.isShutdown()) exe.shutdown();
//        exe = Executors.newSingleThreadExecutor();
//        Future<Socket> result = exe.submit(new Task(iNet, port));
//
//
//
//        return result;
//    }
//
//
//    public static Future<Integer> Server() {
//
//        if (exe != null && !exe.isShutdown()) exe.shutdown();
//        exe = Executors.newSingleThreadExecutor();
//        Future<Integer> result = exe.submit(new Task());
//
////        result.
//
//        Socket mSocket;
//        try {
//            ServerSocket ss = new ServerSocket();
//            mSocket = ss.accept();
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//        return result;
//    }
//
//    private static class Task implements Callable<Socket> {
//        String iNet;
//        int port;
//
//        public Task(String iNet, int port) {
//            this.iNet = iNet;
//            this.port = port;
//        }
//
//        @Override
//        public Socket call() throws Exception {
//            Socket mSocket = new Socket();
//            SocketAddress addr = new InetSocketAddress(iNet, port);
//            try {
//                mSocket.connect(addr, 3000);
//            } catch (IOException e) {
//                e.printStackTrace();
//                return null;
//            }
//            return mSocket;
//        }
//    }
//}
