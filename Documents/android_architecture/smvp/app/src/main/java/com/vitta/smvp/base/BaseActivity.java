package com.vitta.smvp.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.vitta.smvp.util.TypeUtil;

/**
 * 作者：王文婷 邮箱：WVitta@126.com
 * 创建时间：2017/12/25 14:21
 * 描述：BaseActivity
 *     ① 获取p 对象（泛型传进来） ；②调用p 生命周期方法 ，与view 同步
 */

public abstract class BaseActivity<P extends BasePresenter> extends RootActivity implements BaseView {
    private static final String TAG = "BaseActivity";

    protected P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //①
        mPresenter = TypeUtil.getObject(this,0);
        if (mPresenter != null){
            mPresenter.attachView(this);
        }
        super.onCreate(savedInstanceState);
    }

    //②管理生命周期
    @Override
    protected void onDestroy() {
        if (mPresenter != null){
            mPresenter.detachView();
        }
        super.onDestroy();
    }

    //-----------view 的回调方法---------------------------------------------------------------------
//    @Override
//    public void stateMain() {
//
//    }
//
//    @Override
//    public void stateLoading() {
//
//    }
//
//    @Override
//    public void stateEmpty() {
//
//    }
//
//    @Override
//    public void stateError() {
//
//    }
//
//    @Override
//    public void showErrorMessage(String message) {
//        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
//    }
    //-----------view 的回调方法---------------------------------------------------------------------
}
