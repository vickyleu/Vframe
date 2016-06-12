package com.esapos.vicky.Controller.Test;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Administrator on 2016/6/1.
 */
public interface Services {
    @GET("/url")
    Observable get();
}
