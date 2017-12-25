package com.vitta.smvp.model.http;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.vitta.smvp.base.BaseCodeBeen;
import com.vitta.smvp.model.http.api.ApiService;
import com.vitta.smvp.model.http.been.MineFansBeen;

import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 作者：王文婷 邮箱：WVitta@126.com
 * 创建时间：2017/12/25 16:07
 * 描述：RetrofitHelper
 */

public class RetrofitHelper implements HttpHelper {

    private static RetrofitHelper helper;

    private RetrofitHelper() {
    }

    public static RetrofitHelper getInstance(){
        if (helper == null){
            synchronized (RetrofitHelper.class){
                if (helper == null){
                    helper = new RetrofitHelper();
                }
            }
        }
        return helper;
    }

    //静态的相当于是单例的
    private static ApiService apiService;

    public static ApiService getApiService(){
        if (apiService == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.xiaomatv.cn")
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            apiService = retrofit.create(ApiService.class);
        }
        return apiService;
    }


    @Override
    public Flowable<BaseCodeBeen<List<MineFansBeen>>> getFans(Map<String, String> map) {
        return getApiService().getMineFansBeen(map);
    }
}
