package com.example.demosystem.helper;

import android.os.Environment;

import com.example.demosystem.NetService.RetrofitApiService;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

//TODO:用该类代替NetworkHelper
public class RetrofitManager {
    private static RetrofitManager retrofitManager;
    private OkHttpClient okHttpClient;
    private Retrofit retrofit;
    private RetrofitApiService retrofitApiService;

    private  RetrofitManager(){
        initOkHttpClient();
        initRetrofit();
    }


    public static RetrofitManager getInstance(){
        if(retrofitManager==null){
            synchronized (RetrofitManager.class){
                if(retrofitManager==null){
                    retrofitManager=new RetrofitManager();
                }
            }
        }
        return retrofitManager;
    }

    public static RetrofitApiService getApiService() {
        return getInstance().retrofitApiService;
    }

    private void initOkHttpClient() {
        okHttpClient = new OkHttpClient.Builder()
                //设置缓存文件路径，和文件大小
                .cache(new Cache(new File(Environment.getExternalStorageDirectory() + "/okhttp_cache/"), 50 * 1024 * 1024))
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();

    }


    private void initRetrofit(){
        retrofit=new Retrofit.Builder().baseUrl(SystemHelper.getInstance().getApiUrl())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitApiService=retrofit.create(RetrofitApiService.class);
    }



}
