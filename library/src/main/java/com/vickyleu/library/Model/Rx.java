package com.vickyleu.library.Model;

import rx.Subscriber;

/**
 * Created by Administrator on 2016/6/2.
 */
public  interface Rx{
    <T> void sendNext(Subscriber<T> subscriber,T t);
}
