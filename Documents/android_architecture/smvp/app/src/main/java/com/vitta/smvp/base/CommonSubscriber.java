package com.vitta.smvp.base;

import android.text.TextUtils;
import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.vitta.smvp.model.http.exception.ApiException;

import java.util.List;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 * 作者：王文婷 邮箱：WVitta@126.com
 * 创建时间：2017/12/22 16:16
 * 描述：CommonSubscriber
 */

public abstract class CommonSubscriber<T> extends ResourceSubscriber<T> {
    private static final String TAG = "CommonSubscriber";

    private BaseView mView;
    private String mErrorMsg;
    private boolean isShowErrorState = true;

    //构造方法---------------------------------------------------------------------------------------
    public CommonSubscriber(BaseView mView) {
        this.mView = mView;
    }

    public CommonSubscriber(BaseView mView, String errorMsg) {
        this.mView = mView;
        this.mErrorMsg = errorMsg;
    }

    public CommonSubscriber(BaseView mView, String errorMsg, boolean isShowErrorState) {
        this.mView = mView;
        this.mErrorMsg = errorMsg;
        this.isShowErrorState = isShowErrorState;
    }

    public CommonSubscriber(BaseView mView, boolean isShowErrorState) {
        this.mView = mView;
        this.isShowErrorState = isShowErrorState;
    }
    //构造方法---------------------------------------------------------------------------------------

    //提供给外面去实现
    //尝试处理一波 t为null 和 size = 0
    @Override
    public void onNext(T t) {
        if (t == null){
            mView.stateEmpty();
            return;
        }
        if (t instanceof List){
            if (((List) t).size() > 0){
                onNextNotEmpty(t);
                mView.stateMain();
            }else{
                mView.stateEmpty();
            }
        }else {
            onNextNotEmpty(t);
        }
    }

    protected abstract void onNextNotEmpty(T t);

    @Override
    public void onError(Throwable t) {
        if (mView == null) return;
        if (!TextUtils.isEmpty(mErrorMsg)) {
            mView.showErrorMessage(mErrorMsg);
        } else if (t instanceof ApiException) {
            mView.showErrorMessage(t.getMessage());
        } else if (t instanceof HttpException) {
            mView.showErrorMessage("数据加载失败ヽ(≧Д≦)ノ");
        } else {
            mView.showErrorMessage("未知错误ヽ(≧Д≦)ノ");
            Log.e(TAG, "onError: " + t.toString());
        }

        if (isShowErrorState) {
            mView.stateError();
        }
    }

    @Override
    public void onComplete() {

    }
}
