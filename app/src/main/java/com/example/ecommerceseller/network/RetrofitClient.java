package com.example.ecommerceseller.network;


import com.example.ecommerceseller.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private String CUSTOMER_KEY = BuildConfig.CONSUMER_KEY;
    private String CUSTOMER_SECERT =BuildConfig.CONSUMER_SEECRET;

    private static RetrofitClient retrofitClient;
    private ApiService apiService;

    public static RetrofitClient getInstance(){
        if (retrofitClient==null)
            retrofitClient=new RetrofitClient();
        return retrofitClient;
    }

    public ApiService getApiService(){
        getInstance();
        if (apiService!=null)
            return apiService;
         OAuthInterceptor oauth1Woocommerce = new OAuthInterceptor.Builder()
                .consumerKey(CUSTOMER_KEY)
                .consumerSecret(CUSTOMER_SECERT)
                .build();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .addInterceptor(oauth1Woocommerce)// Interceptor oauth1Woocommerce added
                .build();

        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService=mRetrofit.create(ApiService.class);
        return apiService;

    }




}
