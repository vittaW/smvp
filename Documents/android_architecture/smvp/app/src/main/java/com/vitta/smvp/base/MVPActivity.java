package com.vitta.smvp.base;

import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.vitta.smvp.R;
import com.vitta.smvp.util.TypeUtil;

/**
 * 作者：王文婷 邮箱：WVitta@126.com
 * 创建时间：2017/12/27 11:09
 * 描述：MVPActivity 封装状态控制的逻辑
 */

public abstract class MVPActivity<P extends MVPresenter> extends BaseActivity<P> {

    private ViewGroup view_main;
    private ViewGroup mParent;
    private View view_loading;
    private View view_empty;
    private View view_error;
    private int STATE_MAIN = 100;
    private int STATE_LOADING = 101;
    private int STATE_ERROR = 102;
    private int STATE_EMPTY = 103;
    private int currentState = STATE_MAIN;
    private boolean isRefresh = true;

    public void setRefresh(boolean refresh) {
        isRefresh = refresh;
    }

    @Override
    protected void initView() {
        //拿到view_main 和 mParent
        view_main = (ViewGroup) findViewById(R.id.view_main);
        if (view_main == null) {
            throw new IllegalStateException("封装页面状态的activity 必须含有一个主内容区域 view_mian ，通常是RecyclerView");
        }
        if (view_main.getParent() == null) {
            throw new IllegalStateException("封装页面状态的activity 主内容必须有根节点 mParent");
        }
        if (!(view_main.getParent() instanceof ViewGroup)) {
            throw new IllegalStateException("主内容的parent 必须是ViewGroup");
        }
        mParent = ((ViewGroup) view_main.getParent());
        view_main.setVisibility(View.VISIBLE);
    }

    @Override
    public void stateMain() {
        if (isRefresh){
            if (currentState == STATE_MAIN) return;
            hideCurrentView();
            currentState = STATE_MAIN;
            view_main.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void stateLoading() {
        if (isRefresh){
            if (currentState == STATE_LOADING) return;
            hideCurrentView();
            currentState = STATE_LOADING;
            if (view_loading == null) {
                //添加loading view 到parent 里
                View.inflate(this, R.layout.custom_view_loading, mParent);
                view_loading = mParent.findViewById(R.id.view_loading);
            }
            view_loading.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void stateEmpty() {
//        if (isRefresh){
//            if (currentState == STATE_EMPTY) return;
//            hideCurrentView();
//            currentState = STATE_EMPTY;
//            if (view_empty == null) {
//                //添加loading view 到parent 里
//                View.inflate(this, R.layout.custom_view_empty, mParent);
//                view_empty = mParent.findViewById(R.id.view_empty);
//            }
//            view_empty.setVisibility(View.VISIBLE);
//        }

    }

    @Override
    public void stateError() {
        if (isRefresh){
            if (currentState == STATE_ERROR) return;
            hideCurrentView();
            currentState = STATE_ERROR;
            if (view_error == null) {
                //添加loading view 到parent 里
                View.inflate(this, R.layout.custom_view_error, mParent);
                view_error = mParent.findViewById(R.id.view_error);
            }
            view_error.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showErrorMessage(String message) {
        Snackbar.make(mParent, message, Snackbar.LENGTH_SHORT).show();
//        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void hideCurrentView() {
        if (currentState == STATE_MAIN) {
            view_main.setVisibility(View.GONE);
        } else if (currentState == STATE_LOADING && view_loading != null) {
            view_loading.setVisibility(View.GONE);
        } else if (currentState == STATE_EMPTY && view_empty != null) {
            view_empty.setVisibility(View.GONE);
        } else if (currentState == STATE_ERROR && view_error != null) {
            view_error.setVisibility(View.GONE);
        }
    }
}
