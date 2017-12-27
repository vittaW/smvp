package com.vitta.smvp.ui.test;

import com.vitta.smvp.R;
import com.vitta.smvp.base.BaseActivity;

/**
 * 作者：王文婷 邮箱：WVitta@126.com
 * 创建时间：2017/12/27 16:23
 * 描述：TestActivity
 */

public class TestActivity extends BaseActivity<TestPresenter> {
    @Override
    public void stateMain() {

    }

    @Override
    public void stateLoading() {

    }

    @Override
    public void stateEmpty() {

    }

    @Override
    public void stateError() {

    }

    @Override
    public void showErrorMessage(String message) {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_test;
    }

    @Override
    protected void initView() {

    }
}
