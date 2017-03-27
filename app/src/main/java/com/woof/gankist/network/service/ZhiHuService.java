package com.woof.gankist.network.service;

import com.woof.gankist.dao.entity.ZhiHuComment;
import com.woof.gankist.dao.entity.ZhiHuNews;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Woof on 3/21/2017.
 */

public interface ZhiHuService {

    String zhihuBaseUrl = "http://news-at.zhihu.com/api/4";

    @GET("news/latest")
    Call<ZhiHuNews> getZhiHuNews();

    @GET("news/before/{date}")
    Call<ZhiHuNews> getHistory(@Path("date") String date);

    @GET("news/{id}")
    Call<ZhiHuNews> getUserNews(@Path("id") int id);

    @GET("story/{id}/long-comments")
    Call<ZhiHuComment> getZhiHuComment(@Path("id") int id);
}



