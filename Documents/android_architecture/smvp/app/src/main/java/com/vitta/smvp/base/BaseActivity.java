package com.vitta.smvp.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者：王文婷 邮箱：WVitta@126.com
 * 创建时间：2017/12/25 14:12
 * 描述：BaseActivity 封装的都是关于UI View 的操作。 --  不带MVP的架构
 */

public abstract class BaseActivity extends AppCompatActivity{

    //ButterKnife 绑定于解绑
    private Unbinder unBinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        unBinder = ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unBinder != null){
            unBinder.unbind();
        }
    }

    protected abstract int getLayout();
}
