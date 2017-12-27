package com.vitta.smvp.base;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * 作者：王文婷 邮箱：WVitta@126.com
 * 创建时间：2017/12/26 14:56
 * 描述：MVPresenter 通过rxjava 管理presenter 的生命周期（与 view 同步）
 *      通过容器，在detachView 时，取消所有订阅
 */

public class MVPresenter<T extends BaseView> implements BasePresenter<T> {

    //presenter 需要持有view 接口，view 通过泛型传进来
    protected T mView;

    //RXJava 里的接收容器？！！有多个发射序列来着，都会保存在这里
    protected CompositeDisposable mCompositeDisposable;

    //添加订阅（接收者） - 这个方法在添加订阅的时候调用（比如 ：网络请求。。。）
    protected void addSubscribe(Disposable subscription){
        if (mCompositeDisposable == null){
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(subscription);
    }

    //清空所有的接收序列
    protected void unSubscribe(){
        if (mCompositeDisposable != null){
            mCompositeDisposable.clear();
        }
    }

    @Override
    public void attachView(T view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
        unSubscribe();
    }
}
