package com.vitta.smvp.model;

import com.vitta.smvp.base.BaseCodeBeen;
import com.vitta.smvp.model.http.HttpHelper;
import com.vitta.smvp.model.http.RetrofitHelper;
import com.vitta.smvp.model.http.been.MineFansBeen;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;

/**
 * 作者：王文婷 邮箱：WVitta@126.com
 * 创建时间：2017/12/25 15:14
 * 描述：DataManager - SMVP - S!!
 * 主要功能 ： 从不同的model 拿去数据组装成presenter 需要的数据。整个App的数据总控制阀。
 */

public class DataManager {

    private static DataManager dataManager;
    //用来组装数据的model
    private HttpHelper httpHelper;

    //构造私有
    private DataManager(HttpHelper httpHelper) {
        this.httpHelper = httpHelper;
    }

    //提供单例模式
    public static DataManager getInstance(){
        if (dataManager == null){
            synchronized (DataManager.class){
                if (dataManager == null){
                    dataManager = new DataManager(RetrofitHelper.getInstance());
                }
            }
        }
        return dataManager;
    }


    public Flowable<BaseCodeBeen<List<MineFansBeen>>> getFans(Map<String,String> map){
        return httpHelper.getFans(map);
    }
}
