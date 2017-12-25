package com.vitta.smvp.base;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 作者：王文婷 邮箱：WVitta@126.com
 * 创建时间：2017/12/22 16:16
 * 描述：CommenObserver
 */

public abstract class CommenObserver<T> implements Observer<BaseCodeBeen<T>>{

    private BaseView mView;

    public CommenObserver(BaseView mView) {
        this.mView = mView;
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onError(Throwable e) {
        mView.loadError("网络出错了");
    }

    @Override
    public void onComplete() {

    }

}
