package com.woof.gankist.network.service;

import com.woof.gankist.dao.entity.GankEntity;
import com.woof.gankist.dao.entity.GankSerachResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Woof on 3/20/2017.
 */

public interface GankService {

    String gankBaseURL = "http://gank.io/api/";

    @GET("data/{category}/{pagesize}/{page}")
    Call<GankEntity> getGankEntity(@Path("category") String category,
                                   @Path("pagesize") int pageSize,
                                   @Path("page")     int page);


    @GET("search/query/{key}/category/all/count/{count}/page/{page}")
    Call<GankSerachResult> getSearchResult(@Path("key")   String key,
                                           @Path("count") int count,
                                           @Path("page")  int page);
}
