package com.vitta.smvp.base;

import android.support.v7.widget.Toolbar;

/**
 * 作者：王文婷 邮箱：WVitta@126.com
 * 创建时间：2017/12/25 14:21
 * 描述：BasePresenter
 */

public class BasePresenter {

    private MVPActivity activity;
    private BaseView view;

    public BasePresenter(MVPActivity activity, BaseView view) {
        this.activity = activity;
        this.view = view;
    }




}
