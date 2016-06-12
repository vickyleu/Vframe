package com.esapos.vicky.Controller.Test;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by Administrator on 2016/5/30.
 */
public class Rx {

    public void begin() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .build();
        GitHubService service = retrofit.create(GitHubService.class);
        @SuppressWarnings("unchecked")
        Call<List<Repo>> rep = (Call<List<Repo>>) service.overView("vickyleu");
        rep.enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                //从response.body()中获取结果
            }

            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t) {

            }
        });

        lambda();
    }

    Observable<List<String>> query(String text) {
        return Observable.create(subscriber -> {subscriber.onNext(new ArrayList<String>());});
    }

    private void lambda() {
        query("Hello, world!")
                .flatMap(Observable::from)
                .flatMap(this::getTitle)
                .filter(title -> title != null)
                .take(5)
                .doOnNext(this::saveTitle)
                .subscribe(System.out::println);
    }

    private void saveTitle(Object title) {

    }

    private Observable<?> getTitle(String url) {
        return null;
    }


    public interface GitHubService {
        @GET("/users/{user}/repos")
        List<Repo> overView(@Path("user") String user);

    }

    public interface MyService {
        @POST("/user/param")
        List<Repo> overView();

        @POST
        List<Repo> overView(@Url String url, @Body JSONObject obj);
    }

    private class Repo {
    }


}
