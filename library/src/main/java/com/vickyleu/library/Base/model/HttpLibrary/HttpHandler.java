package com.vickyleu.library.Base.model.HttpLibrary;


public interface HttpHandler {
    void httpErr(HttpResponseModel var1) throws Exception;

    void httpSuccess(HttpResponseModel var1) throws Exception;
}

