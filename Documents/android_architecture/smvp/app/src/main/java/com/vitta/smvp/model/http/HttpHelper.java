package com.vitta.smvp.model.http;

import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.vitta.smvp.base.BaseCodeBeen;
import com.vitta.smvp.model.http.been.MineFansBeen;

import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 作者：王文婷 邮箱：WVitta@126.com
 * 创建时间：2017/12/25 14:28
 * 描述：HttpHelper
 */

public interface HttpHelper {

    Flowable<BaseCodeBeen<List<MineFansBeen>>> getFans(Map<String,String> map);

}
