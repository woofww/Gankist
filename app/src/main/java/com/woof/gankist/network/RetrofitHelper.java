package com.woof.gankist.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.woof.gankist.base.BaseApplication;
import com.woof.gankist.network.service.GankService;
import com.woof.gankist.util.NetworkStateUtil;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Woof on 3/20/2017.
 */
public class RetrofitHelper {

    private static GankService sGankService = null;
    private static Retrofit sRetrofit = null;
    private static OkHttpClient sOkHttpClient = null;


    public RetrofitHelper(){
        init();
    }

    private void init(){
        initOkHttp();
        initRetrofit();
        // 创建请求
        sGankService = sRetrofit.create(GankService.class);

    }

    /**
     * Construct the connection within Constructor,
     * forbid other class generate the instance from outside
     */

    private static void initOkHttp() {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        /**
         * Build系统生成一个名称为BuildConfig的类，该类包含一个DEBUG 常量，该常量会根据您的Build类型自动设置
         * 值。您可以通过(BuildConfig.DEBUG) 常量来编写只在Debug模式下运行的代码。
         */

//        if(BuildConfig.DEBUG) {
//            // 查看网络日志：HttpLoggingInterceptor 是一个拦截器，用于输出网络请求和结果的 Log
//            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
//        }
        /**
         * 缓存机制，无网的时候进行数据展示
         */
        File cacheFile = new File(BaseApplication.getAppCacheDir(), "/NetCache");
        // According to use the OkHttp Cache mechanism to store the network response
        Cache cache = new Cache(cacheFile, 1024*1024*30);
        Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!NetworkStateUtil.isNetworkConnected(BaseApplication.getAppContext())){
                    // 如果网络状态不可用，设置缓存
                    request = request
                            .newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }

                Response response = chain.proceed(request);

                if (NetworkStateUtil.isNetworkConnected(BaseApplication.getAppContext())){
                    // 如果网络可用，设置缓存超时时间为0
                    int maxAge = 0;
                    response.newBuilder()
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .build();

                } else {
                    // 如果网路不可用，设置超时时间为2周
                    int maxStale = 60 * 60 * 24 * 14;
                    response.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale);
                }
                return response;

            }
        };
        builder.cache(cache).addInterceptor(cacheInterceptor);
        // 设置超时
        builder.connectTimeout(15, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);
        // 错误重连
        builder.retryOnConnectionFailure(true);
        sOkHttpClient = builder.build();
    }

    private static void initRetrofit() {
        /*Retrofit初始化*/
        sRetrofit = new Retrofit.Builder()
                .baseUrl("http://gank.io/api/")
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setDateFormat("yyyy-MM-dd").create()))
                .build();
    }

    public static RetrofitHelper getInstance(){return RetrofitHolder.INSTANCE;}

    public GankService getGankService(){ return sGankService;}

    private static class RetrofitHolder {
        /* generate the retrofit instance by holder */
        private static final RetrofitHelper INSTANCE = new RetrofitHelper();
    }

}