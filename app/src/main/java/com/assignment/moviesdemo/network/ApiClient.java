package com.assignment.moviesdemo.network;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static volatile ApiClient instance;
    private ApiServices apiServices;
    private final static String BASE_URL = "https://api.themoviedb.org/3/";

    private ApiClient(){
        initApiServices();
    }

    public static ApiClient getInstance(){
        if(instance == null){
            synchronized (ApiClient.class){
                if(instance == null){
                    instance = new ApiClient();
                }
            }
        }
        return instance;
    }

    private void initApiServices(){
        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiServices = retrofit.create(ApiServices.class);
    }

    public ApiServices getApiServices(){
        return apiServices;
    }
}
